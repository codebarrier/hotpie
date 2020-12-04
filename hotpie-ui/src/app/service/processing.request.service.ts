import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http'
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Group } from '../model/group.model';
import { ProcessorPipelineProfile } from '../model/processorprofile.model';
import { Data } from '../model/data.model';

@Injectable()
export class ProcessingRequestService {

    constructor(
        private httpClient: HttpClient
    ){}

    private retrievedData: Group[];
    private selectedData: Data;
    private selectedGroup: Group;

    postDataFile(fileToUpload: File, profile: ProcessorPipelineProfile): Observable<Object> {
        const endpoint = 'processDataWithProfileConfig';
        const formData: FormData = new FormData();
        formData.append('dataFile', fileToUpload, fileToUpload.name);
        formData.append('profile', JSON.stringify(profile));
        return this.httpClient
          .post(endpoint, formData, { headers: {
              'content' :'multipart/form-data'
          }}).pipe(catchError(this.handleError));// .pipe(map((resp: HttpResponse<Group[]>) => resp.body));
    }

    postDataText(manualText: string, profile: ProcessorPipelineProfile): Observable<Object> {
        const endpoint = 'processRawDataWithProfileConfig';
        const formData: FormData = new FormData();
        formData.append('textData', manualText);
        formData.append('profile', JSON.stringify(profile));
        return this.httpClient
          .post(endpoint, formData, { headers: {
            'content' :'multipart/form-data'
          }}).pipe(catchError(this.handleError));// .pipe(map((resp: HttpResponse<Group[]>) => resp.body));
    }

    postFile(fileToUpload: File, profileName: string): Observable<Object> {
        const endpoint = 'processData';
        const formData: FormData = new FormData();
        formData.append('dataFile', fileToUpload, fileToUpload.name);
        formData.append('profileName', profileName);
        return this.httpClient
          .post(endpoint, formData, { headers: {
              'content' :'multipart/form-data'
          }}).pipe(catchError(this.handleError));// .pipe(map((resp: HttpResponse<Group[]>) => resp.body));
    }

    postText(manualText: string, profileName: string): Observable<Object> {
        const endpoint = 'processTextData';
        const formData: FormData = new FormData();
        formData.append('textData', manualText);
        formData.append('profileName', profileName);
        return this.httpClient
          .post(endpoint, formData, { headers: {
              'content' :'application/text'
          }}).pipe(catchError(this.handleError));// .pipe(map((resp: HttpResponse<Group[]>) => resp.body));
    }

    private handleError(error): Observable<Response> {
        console.log('Error executing REST request.')
        return throwError(error);
    }

    setRetrievedData(data: Group[]) {
        this.retrievedData = data;
    }

    getRetrievedData(): Group[] {
        return this.retrievedData;
    }

    setSelectedData(data: Data) {
        this.selectedData = data;
    }

    getSelectedData(): Data {
        return this.selectedData;
    }

    setSelectedGroup(group: Group) {
        this.selectedGroup = group;
    }

    getSelectedGroup() {
        return this.selectedGroup;
    }
}