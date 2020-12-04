import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Group } from '../model/group.model';
import { ProcessingRequestService } from '../service/processing.request.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-data-navigator',
  templateUrl: './data-navigator.component.html',
  styleUrls: ['./data-navigator.component.scss']
})
export class DataNavigatorComponent implements OnInit {

  public collapsed: string;

  totalGroupData: Group[];
  @Output() selectedGroupForReport = new EventEmitter<Group>();

  constructor(private processorService: ProcessingRequestService, private router: Router) { 
    this.totalGroupData = this.processorService.getRetrievedData();
  }

  ngOnInit(): void {
  }

  getChildren = (group: Group) => {
    if (group && group.subGroups && group.subGroups.length > 0) {
      return group.subGroups;
      // return this.folderService.getFiles(folder);
    } else {
      return null;
    }
  };

  showSelectedGroup(selectedGroup: Group) {
    console.log('Setting selected group to ' + selectedGroup.groupName);
    this.deActivateAllGroups(this.totalGroupData[0]);
    this.activateGroups(selectedGroup, this.totalGroupData[0]);  
    this.processorService.setSelectedData(null);
    this.processorService.setSelectedGroup(selectedGroup);
  }

  @Output() showSeverityReport = new EventEmitter<boolean>();


  triggerSeverityReport() {
  }

  deActivateAllGroups(groupSet: Group) {
    groupSet.active=false;
    for (let index = 0; index < groupSet.subGroups.length; index++) {
      const element = groupSet.subGroups[index];
      this.deActivateAllGroups(element);
    }
  }

  activateGroups(searchedGroup: Group, groupSet: Group): boolean {

    if (searchedGroup.groupId === groupSet.groupId) {
      groupSet.active = true;
      console.log('Group active is : ' + groupSet.groupName);
      return true;
    }

    for (let index = 0; index < groupSet.subGroups.length; index++) {
      const element = groupSet.subGroups[index];
      if (this.activateGroups(searchedGroup, element)) {
        element.active=true;
        console.log('Group active is : ' + groupSet.groupName);
        return true;
      } else {
        element.active=false;
      }
    }

    return false;
  }

}
