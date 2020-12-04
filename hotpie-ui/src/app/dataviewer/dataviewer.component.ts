import { Component, OnInit, Input, AfterViewInit, ViewChild } from '@angular/core';
import { Group } from '../model/group.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ProcessingRequestService } from '../service/processing.request.service';
import {ClrDatagridStringFilterInterface} from "@clr/angular";
import { Data } from '../model/data.model';
import { DatareportComponent } from '../datareport/datareport.component';

class DataFilter implements ClrDatagridStringFilterInterface<Data> {
    accepts(data: Data, search: string):boolean {
        return data.data.toLowerCase().search(search) > 0;
    }
}

@Component({
  selector: 'app-dataviewer',
  templateUrl: './dataviewer.component.html',
  styleUrls: ['./dataviewer.component.scss']
})
export class DataviewerComponent implements OnInit, AfterViewInit {

  public selectedGroup: Group = null;
  public selectedData: Data = null;
  groupId: string;
  public dataFilter = new DataFilter();
  public selected: Data;
  public totalRecords: Data[] = [];
  public detailData: Data;
  public currentPage: number = 1; 
  public totalGroupData: Group[];
  public showSeverityReport: boolean = false;

  constructor(private route: ActivatedRoute, private processorService: ProcessingRequestService, private router: Router) {
    this.totalGroupData = this.processorService.getRetrievedData();
  }

  ngAfterViewInit(): void {
    console.log('After view initialzation called.')
  }

  showSeverityReportUI(show: boolean) {
    this.router.navigate(['/processedData/severityreport'], { skipLocationChange: true });
  }

  @ViewChild(DatareportComponent) datareportComponent: DatareportComponent;

  onRowSelection(selectedRow: Data) {
    console.log('Data selected '+ selectedRow);
    this.deActivateAllGroups(this.totalGroupData[0]);
    this.totalGroupData[0].active=true;
    this.totalGroupData[0].expanded=true;
    this.processorService.setSelectedData(selectedRow);
    this.processorService.setSelectedGroup(this.totalGroupData[0]);
    this.router.navigate(['/processedData', 'datareport', this.totalGroupData[0].groupId, 'changer'], { skipLocationChange: true }).then(() => {
      this.router.navigate(['/processedData', 'datareport', this.totalGroupData[0].groupId, selectedRow.dataId], { skipLocationChange: true })
    });
  }

  showSelectedGroup(group: Group) {
    if (group) {
      this.selectedGroup = group;
      this.showSeverityReportUI(false);
      this.deActivateAllGroups(this.totalGroupData[0]);
      this.activateGroups(group, this.totalGroupData[0]);
      console.log('Selected group is ' + this.selectedGroup.groupName);
      this.totalRecords = this.accumulateData([], this.selectedGroup);
      console.log(this.selectedGroup.highlights);
      console.log(this.selectedGroup.checkpoints);
    }
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

  ngOnInit(): void {
    console.log('Dataview Initialization...');
  }

  accumulateData(totalRecords: Data[], group: Group): Data[] {
    if (group && group.groupData && group.groupData.length > 0) {
      if (!totalRecords) {
        totalRecords = [];
      }
      totalRecords = totalRecords.concat(group.groupData);
      return totalRecords;
    } else if (group && group.subGroups && group.subGroups.length > 0) {
      for (let index = 0; index < group.subGroups.length; index++) {
        const grp = group.subGroups[index];
        totalRecords = totalRecords.concat(this.accumulateData([], grp));

      }
      return totalRecords;
    }
  }
}
