import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ProcessingRequestService } from '../service/processing.request.service';
import { Observable } from 'rxjs';
import { Group } from '../model/group.model';
import { Router, Routes, Route } from '@angular/router';
import { DataviewerComponent } from '../dataviewer/dataviewer.component';
import { ClrLoadingState } from '@clr/angular';
import { ProcessorPipelineProfile } from '../model/processorprofile.model';
import { ProfileService } from '../service/profile.service';

@Component({
  selector: 'app-processor',
  templateUrl: './processor.component.html',
  styleUrls: ['./processor.component.scss']
})
export class ProcessorComponent implements OnInit {

  constructor(private processorService: ProcessingRequestService, private router: Router, private profileService: ProfileService) { }

  dataFileToUpload: File = null;
  profileFileToUpload: File = null;
  disabled = false;
  public isUpload: string = "true";
  public inputText: string = "";
  processBtnState: ClrLoadingState = ClrLoadingState.DEFAULT;
  public allProfiles: ProcessorPipelineProfile[];
  public profilesToDisplay: ProcessorPipelineProfile[] = [];
  fieldText: String = "Enter filename";
  vertical = '';
  showLabel: boolean = false;
  selectedProfileName: string;
  selectedProfileRow: ProcessorPipelineProfile;
  selectedProfileLabel: string = 'Select Profile';

  processingCriteriaNotSatisfied: boolean = true;
  isManualDataAvailable: boolean = false;
  isDataAvailable: boolean = false;
  dataFileSelectedForUpload: boolean = false;
  profileFileSelectedForUpload: boolean = false;
  profileSelectedForProcessing: boolean = false;
  selectedProfile: String = "";
  totalGroupData: Group[];
  public selectedGroup: Group;
  public isLoading: boolean = false;
  profileFilter: string;
  processingFailed: boolean = false;

  model = new FormGroup({
    item: new FormControl('', [Validators.required, Validators.minLength(4)]),
  });

  selectProfile(profile: ProcessorPipelineProfile) {
    this.selectedProfileLabel = profile.profileName;
    this.selectedProfileRow = profile;
  }

  applyFilter(filterValue: string)  {
    setTimeout(() => {
      this.revisitDisplayedProfiles();
    }, 250);
  }

  revisitDisplayedProfiles() {
    this.profilesToDisplay = [];
    this.allProfiles.forEach((profile) => {
      if (profile.profileName.toLowerCase().includes(this.profileFilter.toLowerCase())) {
        this.profilesToDisplay.push(profile);
      }
    });
  }

  changeSelectionMode() {
    console.log('Selection mode : '+ this.isUpload);
    this.inputText = null;
    this.isDataAvailable = false;
  }

  getChildren = (group: Group) => {
    if (group && group.subGroups && group.subGroups.length > 0) {
      return group.subGroups;
    } else {
      return null;
    }
  };

  getDisplayData(group: Group) {

    if (group.subGroups.length > 0) {
      return group.groupName
    } else {
      return group.groupData;
    }
  }

  submit() {
    console.log('Form submit');
    this.processBtnState = ClrLoadingState.LOADING;
    this.isLoading = true;
    let response;
    if (this.isUpload) {
      response = <Observable<Group[]>>this.processorService.postDataFile(this.dataFileToUpload, this.selectedProfileRow);
    } else {
      response = <Observable<Group[]>>this.processorService.postDataText(this.inputText, this.selectedProfileRow);
    }

    response.subscribe(resp => {
      console.log('Data sent for processing.');
      resp[0].active = "true";
      this.totalGroupData = resp;
      this.processorService.setRetrievedData(this.totalGroupData);
      this.router.navigate(['/processedData/datareport/0/0'], { skipLocationChange: true });
      this.isLoading = false;
      this.processBtnState = ClrLoadingState.SUCCESS;
      this.processingFailed = false;
      console.log('Received response : {}', this.totalGroupData);
    }, error => {
      console.log('Text Processing Failed.', error);
      this.processBtnState = ClrLoadingState.DEFAULT;
      this.processingFailed = true;
    }, () => {
      console.log('Text Processed Successfully.');
      this.processingFailed = false;
    })
  }

  ngOnInit(): void {
    this.profileService.getAllprofiles().subscribe(profiles => {
      this.allProfiles = profiles as ProcessorPipelineProfile[];
      this.profilesToDisplay = [];
      this.allProfiles.forEach((profile) => {
        this.profilesToDisplay.push(profile);
      });
    });
  }

  validateManualData() {
    console.log('validating manual data ' + this.isManualDataAvailable);
    if (!this.isUpload) {
      if (this.inputText.length > 0) {
        console.log('Found input text');
        this.isManualDataAvailable = true;
        this.isDataAvailable = true;
        this.processingCriteriaNotSatisfied = !this.isManualDataAvailable || !this.selectedProfileRow;
      } else {
        console.log('Did not find input text');
        this.isManualDataAvailable = false;
        this.isDataAvailable = false;
        this.processingCriteriaNotSatisfied = !this.isManualDataAvailable || !this.selectedProfileRow;
      }
    }
    console.log('manual Data ' + this.isManualDataAvailable);
    console.log('processingCriteriaNotSatisfied Data ' + this.processingCriteriaNotSatisfied);
  }

  handleDataFileInput(files: FileList) {
    if (files.length > 0) {
      console.log('Data File Selected for processing.')
      this.dataFileSelectedForUpload = true;
      this.isDataAvailable = true;
      this.processingCriteriaNotSatisfied = !this.dataFileSelectedForUpload || !this.selectedProfileRow;
      this.dataFileToUpload = files.item(0);
    } else {
      console.log('No File Selected for processing.')
      this.dataFileSelectedForUpload = false;
      this.isDataAvailable = false;
      this.processingCriteriaNotSatisfied = true;
      this.dataFileToUpload = null;
    }
  }

  handleProfileFileInput(files: FileList) {
    if (files.length > 0) {
      console.log('Processor File Selected for processing.')
      this.profileFileSelectedForUpload = true;
      if (this.isUpload) {
        this.processingCriteriaNotSatisfied = !this.dataFileSelectedForUpload || !this.profileFileSelectedForUpload;
      } else {
        this.processingCriteriaNotSatisfied = !this.isManualDataAvailable || !this.profileFileSelectedForUpload;
      }
      
      this.profileFileToUpload = files.item(0);
    } else {
      console.log('No File Selected for processing.')
      this.profileFileSelectedForUpload = false;
      this.processingCriteriaNotSatisfied = true;
      this.profileFileToUpload = null;
    }
  }

  goToProfileConfig() {
    this.router.navigate(['/profile'], { skipLocationChange: false });
  }

  goToDocumentation() {
    this.router.navigate(['/documentation/overview'], { skipLocationChange: false });
  }
}
