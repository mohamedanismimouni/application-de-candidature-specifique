import { mixinHasStickyInput } from '@angular/cdk/table';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import { Observable } from 'rxjs';

import { Candidate } from 'src/app/candidate/class/candidate';
import { Skill } from 'src/app/candidate/class/skill';

import { CandidacyService } from 'src/app/candidate/service/candidacy.service';
import {ResultTestQuestion} from '../../candidate/class/result-test-question';
import {DialogService, DynamicDialogRef} from 'primeng/dynamicdialog';
import {ResultDetailsComponent} from '../../candidate/result-details/result-details.component';
import {saveAs} from 'file-saver';
import {Router} from '@angular/router';
import * as FileSaver from 'file-saver';
import {CandidacyType} from '../../candidate/class/candidacy-type';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {ConfirmationService, MessageService} from 'primeng/api';
import {ICollaborator} from '../../models/collaborator.model';
import {Entretien} from "../../candidate/class/entretien";


@Component({
  selector: 'app-candidates',
  templateUrl: './candidates.component.html',
  styleUrls: ['./candidates.component.scss'],
    animations: [
        trigger('rowExpansionTrigger', [
            state('void', style({
                transform: 'translateX(-10%)',
                opacity: 0
            })),
            state('active', style({
                transform: 'translateX(0)',
                opacity: 1
            })),
            transition('* <=> *', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
        ])
    ]
})
export class CandidatesComponent implements OnInit, OnDestroy {

        public candidates?: Array<Candidate>;
        ref: DynamicDialogRef;
        cols: any[];
        exportColumns: any[];
        candidacyTypes: Array<CandidacyType> = [{
            candidacyTypeName: 'On job'
        },
            {
                candidacyTypeName: 'spontaneous'
            }];
        selectedCandidacyType?: CandidacyType;
        HrResponsibles?: Array<ICollaborator>;
        Managers?: Array<ICollaborator>;
        Collaborators?: Array<ICollaborator>;
        entretienRH: Entretien = {};
        entretienTechnique: Entretien = {};
        entretienTelephonique?: Entretien = {} ;

    constructor(private dialogService: DialogService, private candidacyService: CandidacyService, private router: Router,
                private confirmationService: ConfirmationService, private messageService: MessageService) {
        }

        ngOnInit(): void {
            this.getAll();
            this.findCollabsByProfileType();
            console.log(this.HrResponsibles);
        }

        public getAll(): any {
            this.candidacyService.getCandidates().subscribe((response: Candidate[]) => {
                    this.candidates = response;
                    for (const candidate of this.candidates) {
                        this.candidacyService.getTestByCandidateId(candidate.id).subscribe((data) => {
                            candidate.test = data;
                            candidate.done = data.done;
                            candidate.score = (data.totalScore).toFixed(2);
                        });
                    }
                },
                (error: HttpErrorResponse) => {
                    alert(error.message);
                }
            );
            this.cols = [
                { field: 'Full name', header: 'Full name', customExportHeader: 'Candidate Full Name' },
                { field: 'Phone number', header: 'Phone number' },
                { field: 'Quiz\'s score', header: 'Quiz\'s Score' },
                { field: 'Candidate status', header: 'Candidate Status' }
            ];

            this.exportColumns = this.cols.map(col => ({title: col.header, dataKey: col.field}));
        }
        show(idTest: number) {
            this.ref = this.dialogService.open(ResultDetailsComponent, {
                data: {
                    id: idTest
                },
                header: 'Quiz Details',
                width: '70%',
                contentStyle: {'max-height': '500px', 'overflow': 'auto'},
                baseZIndex: 10000
            });
        }
    downloadFile(id: number, firstname: string, lastname: string) {
            this.candidacyService.getCvByCandidateId(id , firstname , lastname).subscribe((data) => {
                    saveAs(new Blob([data], { type: 'application/pdf'}), 'CV_' + firstname + '_' + lastname);
            });
    }
    ngOnDestroy() {
        if (this.ref) {
            this.ref.close();
        }
    }
    goCandidateDetails(id: number) {
            this.router.navigate(['/app/hr-responsible/candidates/candidateDetails/' + id]);
    }
    exportPdf() {

    }

    exportExcel() {
        import('xlsx').then(xlsx => {
            const worksheet = xlsx.utils.json_to_sheet(this.candidates);
            const workbook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };
            const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
            this.saveAsExcelFile(excelBuffer, 'candidates');
        });
    }
    saveAsExcelFile(buffer: any, fileName: string): void {
        const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        const EXCEL_EXTENSION = '.xlsx';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE
        });
        FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
    getCandidatesByCandidateType(type: string) {
            if (this.selectedCandidacyType === null) {
                this.candidacyService.getCandidates().subscribe((candidates) => {
                    this.candidates = candidates;
                });
            } else {
                this.candidacyService.getCandidatesByCandidacyType(type).subscribe((candidates) => {
                    this.candidates = candidates;
                    for (const candidate of this.candidates) {
                            this.candidacyService.getTestByCandidateId(candidate.id).subscribe((data) => {
                                    candidate.test = data;
                                    candidate.done = data.done;
                                    candidate.score = (data.totalScore).toFixed(2);
                            });
                        }
                    },
                    (error: HttpErrorResponse) => {
                        alert(error.message);
                });
            }
    }
    refuseCandidate(id: number, event: Event) {
            this.confirmationService.confirm({
                target: event.target,
                message: 'Are you sure that you want to proceed?',
                icon: 'pi pi-exclamation-triangle',
                accept: () => {
                    this.candidacyService.rejectCandidate(id).subscribe((data) => {
                        console.log(data);

                    });
                    this.messageService.add({severity: 'success', summary: 'sucess', detail: 'Candidate rejected successfully'});
                },
                reject: () => {
                }
            });
    }
    showConfirm() {
        this.messageService.clear();
        this.messageService.add({severity: 'success', summary: 'sucess', detail: 'Quiz Sent successfully  '});
    }
    sendTest(id: number , email: string) {
        this.candidacyService.sendTest(id).subscribe((data) => {
            if (data === true) {
                this.messageService.clear();
                this.messageService.add({key: 'c', sticky: true, severity:'warn', summary: 'Are you sure you want to send Quiz to ' + email, detail:'Confirm to proceed'});
            }
        });
    }
    findCollabsByProfileType() {
            this.candidacyService.findCollaboratorsByProfileType('rh-responsible').subscribe((hrresponsable) => {
                this.HrResponsibles = hrresponsable;
            });
            this.candidacyService.findCollaboratorsByProfileType('manager').subscribe((manager) => {
                this.Managers = manager;
            });
            this.candidacyService.findCollaboratorsByProfileType('collaborator').subscribe((collab) => {
                this.Collaborators = collab;
            });
    }


    showMessage(email:string){
        
         this.messageService.clear();
        this.messageService.add({severity: 'success', summary: 'sucess', detail: 'interview affected successfully to '+ email});
    }
}






