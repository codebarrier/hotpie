import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from '../service/profile.service';
import { ProcessorPipelineProfile } from '../model/processorprofile.model';
import { saveAs } from 'file-saver';
import { ClrLoadingState } from '@clr/angular';

@Component({
  selector: 'app-profile-config',
  templateUrl: './profile-config.component.html',
  styleUrls: ['./profile-config.component.scss']
})
export class ProfileConfigComponent implements OnInit {

  public allProfiles: ProcessorPipelineProfile[];
  profileFileToUpload: File = null;
  selectedProfile: ProcessorPipelineProfile;
  importSuccess: boolean = false;
  importFailure: boolean = false;
  deleteSuccess: boolean = false;
  deleteFailure: boolean = false;

  @ViewChild('myInput')
  myInputVariable: ElementRef;


  constructor(private router: Router, private profileService: ProfileService) {
  }

  handleProfileFileInput(files: FileList) {
    this.clearAllAlerts();
    console.log('Starting import' + files);
    if (files.length > 0) {
      console.log('Processor File Selected for processing.')
      this.profileFileToUpload = files.item(0);
    } else {
      console.log('No File Selected for processing.')
      this.profileFileToUpload = null;
    }
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      try {
        let processorProfile = JSON.parse(fileReader.result.toString()) as ProcessorPipelineProfile;
        console.log('Trying to import...');
        this.profileService.saveProfile(processorProfile).subscribe(data => {
          console.log('Profile Imported Successfully');
          this.getLatestProfiles();
          this.importSuccess = true;
        }, error => {
          console.log('Error loading profile ' + error)
          this.importSuccess = false;
          this.importFailure = true;
        });
      } catch (Error) {
        this.importSuccess = false;
        this.importFailure = true;
        console.log(Error);
      }
    }

    fileReader.onerror = (error) => {
      console.log('Error loading profile ' + error)
      this.importSuccess = false;
      this.importFailure = true;
    }

    fileReader.readAsText(files.item(0));
    this.getLatestProfiles();
    this.myInputVariable.nativeElement.value = "";
  }

  showprofileEditor: boolean = false;
  isManage: boolean = false;

  getLatestProfiles() {
    this.clearAllAlerts();
    this.profileService.getAllprofiles().subscribe(profiles => {
      this.allProfiles = profiles as ProcessorPipelineProfile[];
    });
  }

  ngOnInit(): void {
    this.getLatestProfiles();
  }

  onEdit(profile: ProcessorPipelineProfile) {
    this.clearAllAlerts();
    this.selectedProfile = profile;
    this.showManageProfile();
  }

  onDelete(profile: ProcessorPipelineProfile) {
    this.clearAllAlerts();
    this.profileService.deleteProfile(profile).subscribe(data => {
      console.log('Deleted Successfully.');
      this.getLatestProfiles();
      this.deleteSuccess = true;
      this.deleteFailure = false;
    }, error => {
      this.deleteSuccess = false;
      this.deleteFailure = true;
    });
  }

  onExport(profile: ProcessorPipelineProfile) {
    console.log('Trying to export...');
    var theJSON = JSON.stringify(profile);
    const blob = new Blob([theJSON], { type: 'text/csv' });
    saveAs(blob, 'profile.json');
  }

  showCreateProfile() {
    this.selectedProfile = null;
    this.isManage = false;
    this.showprofileEditor = true;
  }

  showManageProfile() {
    this.isManage = false;
    this.showprofileEditor = true;
  }

  showEditor() {
    this.showprofileEditor = false;
    this.getLatestProfiles();
  }

  goToDocumentation() {
    this.router.navigate(['/documentation/overview'], { skipLocationChange: false });
  }

  clearAllAlerts() {
    this.deleteSuccess = false;
    this.deleteFailure = false;
    this.importFailure = false;
    this.importSuccess = false;
  }

}
