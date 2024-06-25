import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';

import { ICollaboratorDashboardSettings } from './collaborator-dashboard-settings.model';


@Injectable()
export class CollaboratorDashboardService {

    private collaboratorDashboardSettings: BehaviorSubject<ICollaboratorDashboardSettings> = new BehaviorSubject({}) as any;

    setCollaboratorDashboardSettings(collaboratorDashboardSettings: ICollaboratorDashboardSettings) {
        this.collaboratorDashboardSettings.next(collaboratorDashboardSettings);
    }

    getCollaboratorDashboardSettings(): Observable<ICollaboratorDashboardSettings> {
        return this.collaboratorDashboardSettings.asObservable();
    }

}
