import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http'
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Group } from '../model/group.model';
import { ProcessorPipelineProfile } from '../model/processorprofile.model';

@Injectable()
export class ProfileService {

    constructor(
        private httpClient: HttpClient
    ){}

    private retrievedData: Group[];

    saveProfile(profile: ProcessorPipelineProfile): Observable<Object> {
        const endpoint = 'saveProfile';
        return this.httpClient
          .post<ProcessorPipelineProfile>(endpoint, profile, { headers: {
              'content' :'application/json'
          }}).pipe(catchError(this.handleError));
    }

    deleteProfile(profile: ProcessorPipelineProfile): Observable<Object> {
        const endpoint = 'deleteProfile';
        return this.httpClient
          .post<ProcessorPipelineProfile>(endpoint, profile, { headers: {
              'content' :'application/json'
          }}).pipe(catchError(this.handleError));
    }

    getAllprofiles(): Observable<any> {
        const endpoint = 'getAllProfiles';
        return this.httpClient
          .get(endpoint).pipe(catchError(this.handleError));// .pipe(map((resp: HttpResponse<ProcessorPipelineProfile[]>) => resp.body));
    }

    private handleError(error): Observable<Response> {
        console.log('Error executing REST request.')
        return throwError(error);
    }
}