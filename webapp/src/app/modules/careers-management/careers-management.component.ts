import { Component, OnInit } from '@angular/core';

import { NbDialogService } from '@nebular/theme';

import { CareerPathService } from '../../services/career-path.service';
import { CareerStepService } from '../../services/career-step.service';
import { ToastrService } from '../../services/toastr.service';

import { CreateCareerStepDialogComponent } from './create-career-step-dialog/create-career-step-dialog.component';
import { CreateCareerPathDialogComponent } from './create-career-path-dialog/create-career-path-dialog.component';
import { ConfirmationDialogComponent } from '../../components/confirmation-dialog/confirmation-dialog.component';

import { ICareerPath } from '../../models/career-path.model';
import { ICareerStep } from '../../models/career-step.model';
import { ILink } from '../shared/components/graph/models/link.model';
import { INode } from '../shared/components/graph/models/node.model';
import { IGraphConfiguration } from '../shared/components/graph/models/graph-configuration.model';
import { IDialogData } from '../../models/dialog-data.model';
import { IErrorResponse } from '../../models/error-response.model';

import { ToastrStatusEnum } from '../../enumerations/toastr-status.enum';

import { STARTING_POINT } from '../shared/components/graph/constants/graph.constant';


@Component({
    templateUrl: './careers-management.component.html',
    styleUrls: [ './careers-management.component.scss' ]
})
export class CareersManagementComponent implements OnInit {

    nodes: Array<INode>;
    links: Array<ILink>;
    loading=true;
    selectedNodeValue: INode;

    get selectedNode() {
        return this.selectedNodeValue;
    }

    set selectedNode(selectedNode: INode) {
        this.selectedNodeValue = selectedNode;
        if (!this.selectedNode) {
            this.selectedCareerStep = undefined;
            this.inboundCareerPaths = [];
        } else {
            this.selectedCareerStep = this.selectedNode.data;
            this.inboundCareerPaths = this.links
                .filter(link => link.data.toCareerStep.id === this.selectedCareerStep.id)
                .map(link => link.data);
        }
    }

    graphConfiguration: IGraphConfiguration = {
        withStartingPoint: true,
        selectableStartingPoint: true
    };

    selectedCareerStep: ICareerStep;
    inboundCareerPaths: Array<ICareerPath>;

    startingPoint = STARTING_POINT;

    constructor(
        private dialogService: NbDialogService,
        private careerPathService: CareerPathService,
        private careerStepService: CareerStepService,
        private toastrService: ToastrService
    ) { }

    ngOnInit() {
        this.initializeCareersGraph();
    }

    initializeCareersGraph() {
        this.careerPathService.getCareerPaths().subscribe(
            (careerPaths: ICareerPath[]) => {
                this.careerStepService.getCareerSteps().subscribe(
                    (careerSteps: ICareerStep[]) => {
                        this.nodes = careerSteps.map((careerStep) => ({
                            id: careerStep.label.toLowerCase().replace(' ', '-'),
                            label: careerStep.label,
                            data: careerStep,
                            meta: {
                                selectable: true,
                                covered: true,
                                reachable: true
                            }
                        }));
                        this.links = careerPaths.map((careerPath, index) => ({
                            id: 'link-' + index.toString(),
                            source: careerPath.fromCareerStep.label.toLowerCase().replace(' ', '-'),
                            target: careerPath.toCareerStep.label.toLowerCase().replace(' ', '-'),
                            data: careerPath
                        }));
                       this.loading=false
                    }
                );
            }
        );
    }

    hideCareerStepDrawer() {
        this.selectedNode = undefined;
    }

    onAddInitialCareerStep() {
        this.dialogService.open(CreateCareerStepDialogComponent).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.hideCareerStepDrawer();
                    this.initializeCareersGraph();
                }
            }
        );
    }

    onAddNewCareerPath() {
        const unrelatedCareerSteps: ICareerStep[] = this.nodes.filter(
            (node) => {
                if (node.data.id === this.selectedCareerStep.id) {
                    return false;
                }
                for (const link of this.links) {
                    if (
                        (
                            link.data.fromCareerStep.id === node.data.id &&
                            link.data.toCareerStep.id === this.selectedCareerStep.id
                        ) ||
                        (
                            link.data.fromCareerStep.id === this.selectedCareerStep.id &&
                            link.data.toCareerStep.id === node.data.id
                        )
                    ) {
                        return false;
                    }
                }
                return true;
            }
        ).map(node => node.data);
        this.dialogService.open(
            CreateCareerPathDialogComponent,
            {
                context: {
                    data: {
                        selectedCareerStep: this.selectedCareerStep,
                        possibleCareerPathEdges: unrelatedCareerSteps
                    }
                }
            }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.hideCareerStepDrawer();
                    this.initializeCareersGraph();
                }
            }
        );
    }

    onDeleteCareerPath(index: number) {
        const dialogData: IDialogData = {
            title: 'Delete career path',
            content: 'You\'re about to delete the career path between '
                + this.inboundCareerPaths[index].fromCareerStep.label.toLowerCase()
                + ' and ' + this.selectedCareerStep.label.toLowerCase()
                + ' permanently, do you wish to proceed?'
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.careerPathService.deleteCareerPath(this.inboundCareerPaths[index].id).subscribe(
                        () => {
                            this.toastrService.showStatusToastr(
                                'Career path deleted successfully',
                                ToastrStatusEnum.SUCCESS);
                            this.hideCareerStepDrawer();
                            this.initializeCareersGraph();
                        },
                        (error: IErrorResponse) => {
                            switch (error.status) {
                                case 400:
                                    this.toastrService.showStatusToastr(
                                        'Career path connot be deleted',
                                        ToastrStatusEnum.DANGER);
                                    break;
                                case 404:
                                    this.toastrService.showStatusToastr(
                                        'Oups career path is not found, seems like you are seeing '
                                        + 'some outdated data. Careers graph is now refreshed!',
                                        ToastrStatusEnum.DANGER);
                                    this.hideCareerStepDrawer();
                                    this.initializeCareersGraph();
                                    break;
                            }
                        }
                    );
                }
            }
        );
    }

    onDeleteCareerStep() {
        const dialogData: IDialogData = {
            title: 'Delete career step',
            content: 'You\'re about to delete a career step permanently, do you wish to proceed?'
        };
        this.dialogService.open(
            ConfirmationDialogComponent,
            { context: { data: dialogData } }
        ).onClose.subscribe(
            (result: boolean) => {
                if (result) {
                    this.careerStepService.deleteCareerStep(this.selectedCareerStep.id).subscribe(
                        () => {
                            this.toastrService.showStatusToastr(
                                'Career step deleted successfully',
                                ToastrStatusEnum.SUCCESS);
                            this.hideCareerStepDrawer();
                            this.initializeCareersGraph();
                        },
                        (error: IErrorResponse) => {
                            switch (error.status) {
                                case 400:
                                    this.toastrService.showStatusToastr(
                                        'Career step connot be deleted',
                                        ToastrStatusEnum.DANGER);
                                    break;
                                case 404:
                                    this.toastrService.showStatusToastr(
                                        'Oups career step is not found, seems like you are seeing '
                                        + 'some outdated data. Careers graph is now refreshed!',
                                        ToastrStatusEnum.DANGER);
                                    this.hideCareerStepDrawer();
                                    this.initializeCareersGraph();
                                    break;
                            }
                        }
                    );
                }
            }
        );
    }

}
