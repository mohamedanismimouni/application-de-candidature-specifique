import { Component, OnInit } from '@angular/core';

import { CareerPathService } from '../../../services/career-path.service';
import { CareerStepService } from '../../../services/career-step.service';

import { ICareerPath } from '../../../models/career-path.model';
import { ICareerStep } from '../../../models/career-step.model';
import { ILink } from '../../shared/components/graph/models/link.model';
import { INode } from '../../shared/components/graph/models/node.model';
import { IGraphConfiguration } from '../../shared/components/graph/models/graph-configuration.model';

import { STARTING_POINT } from '../../shared/components/graph/constants/graph.constant';


@Component({
    templateUrl: './careers-exploration.component.html',
    styleUrls: [ './careers-exploration.component.scss' ]
})
export class CareersExplorationComponent implements OnInit {

    nodes: Array<INode>;
    links: Array<ILink>;

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
        private careerPathService: CareerPathService,
        private careerStepService: CareerStepService
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
                    }
                );
            }
        );
    }

    hideCareerStepDrawer() {
        this.selectedNode = undefined;
    }

}
