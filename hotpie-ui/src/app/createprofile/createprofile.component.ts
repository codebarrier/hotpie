import { Component, OnInit, ViewChild, Input, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { ClrWizard } from '@clr/angular';
import { ProcessorPipelineProfile } from '../model/processorprofile.model';
import { LineDemarcationProcessor } from '../model/linedemarcation.model';
import { FilterProcessor } from '../model/filter.model';
import { HighlightConfig } from '../model/highlightconfig.model';
import { SeverityConfig } from '../model/severityconfig.model';
import { CheckpointConfiguration } from '../model/checkpointconfig.model';
import { GroupConfig } from '../model/groupconfig.model';
import { saveAs } from 'file-saver'
import { ProfileService } from '../service/profile.service';

@Component({
  selector: 'app-createprofile',
  templateUrl: './createprofile.component.html',
  styleUrls: ['./createprofile.component.scss']
})
export class CreateprofileComponent implements OnInit, AfterViewInit {

  @ViewChild("wizard") wizard: ClrWizard;
  openWizard: boolean = true;
  regexPattern: string;
  sampleData: string;
  filterValue: string;
  filterExclude: boolean;
  filterRegexPattern: string;
  validateRegex: boolean = false;
  highlightRegexPattern: string;
  severityRegexPattern: string;
  selectedSeverity: string = null;
  checkpointRegexPattern: string;
  checkpointName: string;
  checkpointDescription: string;
  disableCheckpoint: boolean = false;
  groupByRegexPattern: string;
  groupName: string;
  summary: string = "";
  summaryCode: string;
  profileFileToUpload: File = null;

  @Input("manageMode")
  isManage: boolean;

  @Input("selectedProfile")
  processorProfile: ProcessorPipelineProfile;

  profileDescription: string;
  profileName: string;

  @Output() showEditor = new EventEmitter<boolean>();


  handleProfileFileInput(files: FileList) {
    if (files.length > 0) {
      console.log('Processor File Selected for processing.')
      this.profileFileToUpload = files.item(0);
    } else {
      console.log('No File Selected for processing.')
      this.profileFileToUpload = null;
    }
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      this.processorProfile = JSON.parse(fileReader.result.toString()) as ProcessorPipelineProfile;
    }
    fileReader.readAsText(files.item(0));
  }

  onSave() {
    console.log('Trying to save...');
    this.profileStoreService.saveProfile(this.processorProfile).subscribe(data => {
      console.log('Profile Saved Successfully');
      this.showEditor.emit(false);
    });
  }

  onCancel() {
    this.showEditor.emit(false);
  }


  onNext() {
    this.summary = JSON.stringify(this.processorProfile);
    this.validateRegex = false;
  }

  addGroup() {
    let groupConfig = new GroupConfig();
    groupConfig.regex = this.groupByRegexPattern;
    groupConfig.name = this.groupName;
    if (!this.processorProfile.groups) {
      this.processorProfile.groups = [];
    }
    this.processorProfile.groups.push(groupConfig);
    this.groupName = null;
    this.groupByRegexPattern = null;
  }

  removeSelectedGroup(groupConfig: GroupConfig) {
    const index: number = this.processorProfile.groups.indexOf(groupConfig);
    if (index !== -1) {
      this.processorProfile.groups.splice(index, 1);
    }
  }


  addCheckpoint() {
    let checkpointConfig = new CheckpointConfiguration();
    checkpointConfig.checkpointHeader = this.checkpointName;
    checkpointConfig.checkpointDescription = this.checkpointDescription;
    checkpointConfig.checkpointIdentificationRegex = this.checkpointRegexPattern;

    if (!this.processorProfile.checkpoints) {
      this.processorProfile.checkpoints = [];
    }
    this.processorProfile.checkpoints.push(checkpointConfig);
    if (this.processorProfile.checkpoints.length > 5) {
      this.disableCheckpoint = true;
    }
    this.checkpointName = null;
    this.checkpointDescription = null;
    this.checkpointRegexPattern = null;
  }

  removeSelectedCheckpoint(checkpointConfig: CheckpointConfiguration) {
    const index: number = this.processorProfile.checkpoints.indexOf(checkpointConfig);
    if (index !== -1) {
      this.processorProfile.checkpoints.splice(index, 1);
    }

    if (this.processorProfile.checkpoints.length < 6) {
      this.disableCheckpoint = false;
    }
  }

  addSeverityRegex() {
    let severityConfig = new SeverityConfig();
    severityConfig.regex = this.severityRegexPattern;
    severityConfig.severity = this.selectedSeverity;
    if (!this.processorProfile.severities) {
      this.processorProfile.severities = [];
    }
    this.processorProfile.severities.push(severityConfig);
    this.severityRegexPattern = null;
    this.selectedSeverity = null;
  }

  removeSelectedSeverityRegex(severity: SeverityConfig) {
    const index: number = this.processorProfile.severities.indexOf(severity);
    if (index !== -1) {
      this.processorProfile.severities.splice(index, 1);
    }
  }


  addHighlightRegex() {
    let highlightConfig = new HighlightConfig();
    highlightConfig.regex = this.highlightRegexPattern;
    if (!this.processorProfile.highlights) {
      this.processorProfile.highlights = [];
    }
    this.processorProfile.highlights.push(highlightConfig);
    this.highlightRegexPattern = null;
  }

  removeSelectedHighlightRegex(highlight: HighlightConfig) {
    const index: number = this.processorProfile.highlights.indexOf(highlight);
    if (index !== -1) {
      this.processorProfile.highlights.splice(index, 1);
    }
  }

  showRegexValidator() {
    this.validateRegex = true;
  }

  constructor(private profileStoreService: ProfileService) {
    this.processorProfile = new ProcessorPipelineProfile();
    console.log('Opening in Manage Mode1.1');
    if (this.isManage) {
      console.log('Opening in Manage Mode1.2');
    }
  }

  ngAfterViewInit(): void {
    if (this.isManage) {
      console.log('Opening in Manage Mode2');
    }
    this.wizard.reset();
  }

  ngOnInit(): void {
    if (!this.processorProfile) {
        this.processorProfile = new ProcessorPipelineProfile();
    }
  }

  addLineDemarcationRegex(regex: string) {
    let lineconfig = new LineDemarcationProcessor();
    lineconfig.lineRegex = regex;
    this.processorProfile.lineDemarcation = lineconfig;
  }

  removeSelectedRegex() {
    this.processorProfile.lineDemarcation = null;
  }

  addFilterRegex() {
    let filterconfig = new FilterProcessor();
    filterconfig.filterRegex = this.filterRegexPattern;
    if (this.filterExclude) {
      filterconfig.isExclude = this.filterExclude;
    } else {
      filterconfig.isExclude = false;
    }
    
    filterconfig.filterValue = this.filterValue;
    if (!this.processorProfile.filters) {
      this.processorProfile.filters = [];
    }
    this.processorProfile.filters.push(filterconfig);
    this.filterExclude = false;
    this.filterValue = null;
    this.filterRegexPattern = null;
  }

  removeSelectedFilter(filter: FilterProcessor) {
    const index: number = this.processorProfile.filters.indexOf(filter);
    if (index !== -1) {
      this.processorProfile.filters.splice(index, 1);
    }
  }
}
