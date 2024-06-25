import { Injectable } from '@angular/core';

import { StorageKeyEnum } from '../enumerations/storage-key.enum';


@Injectable()
export class StorageService {

    constructor() { }

    setItem(key: StorageKeyEnum, value: any): void {
        localStorage.setItem(key, JSON.stringify(value));
    }

    getItem(key: StorageKeyEnum): any {
        const value: string = localStorage.getItem(key);
        if (!value || value === '{}' || value.trim() === '') {
            return null;
        } else {
            return JSON.parse(value);
        }
    }

    removeItem(key: StorageKeyEnum): void {
        localStorage.removeItem(key);
    }

    clearAuthenticationItems(): void {
        this.removeItem(StorageKeyEnum.X_AUTH_TOKEN_KEY);
        this.removeItem(StorageKeyEnum.CURRENT_USER_KEY);
    }

}
