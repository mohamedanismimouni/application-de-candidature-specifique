import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { API_RESOURCES_BASE_URL, API_RESOURCES_PATHS } from '../constants/api-urls.constant';
import { Observable } from 'rxjs';
@Injectable()
export class PayRollService {

    constructor(
        private http: HttpClient
    ) { }
 /**
  * uploadFile
  * @param file 
  * @returns 
  */
    
  uploadPayRoll(file: File ,  payRollDate : string):Observable<void> {
    const url =
        API_RESOURCES_BASE_URL +
        API_RESOURCES_PATHS.PAYROLL_PATHS.UPLOAD_PAYROLL_FILE       
    const data: FormData = new FormData();
    data.append("file", file);
    data.append("payRollDate", payRollDate);
    return this.http.post<void>(url, data);
}

/**
 * 
 * @param file 
 * @param reminderDate 
 * @returns 
 */
uploadReminder(file: File ,  reminderDate : string):Observable<void> {
    const url =
        API_RESOURCES_BASE_URL +
        API_RESOURCES_PATHS.PAYROLL_PATHS.UPLOAD_REMINDER_FILE      
    const data: FormData = new FormData();
    data.append("file", file);
    data.append("reminderDate", reminderDate);
    return this.http.post<void>(url, data);
}
  
}
