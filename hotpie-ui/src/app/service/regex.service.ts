import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class RegexTestService {
 
    constructor(private httpClient: HttpClient) {

    }
    
    public testRegex(regex: string, sampleData: string): Observable<Object>  {
        const endpoint = 'testRegex';
        const formData: FormData = new FormData();
        formData.append('regex', regex);
        formData.append('sampleData', sampleData);
        return this.httpClient
        .post(endpoint, formData, { headers: {
            'content' :'application/text'
        }}).pipe(catchError(this.handleError));
    }

    private handleError(error): Observable<Response> {
        console.log('Error executing REST request.')
        return throwError(error);
    }
}