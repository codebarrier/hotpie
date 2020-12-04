import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Group } from '../model/group.model';
import { ProcessingRequestService } from '../service/processing.request.service';
import { ClrDatagridStringFilterInterface } from '@clr/angular';
import { Data } from '../model/data.model';
import { Router } from '@angular/router';

class DataFilter implements ClrDatagridStringFilterInterface<Data> {
  accepts(data: Data, search: string):boolean {
      return data.data.toLowerCase().search(search) > 0;
  }
}

@Component({
  selector: 'app-severityreport',
  templateUrl: './severityreport.component.html',
  styleUrls: ['./severityreport.component.scss']
})
export class SeverityreportComponent implements OnInit {

  totalGroupData: Group[];
  public alldataRecords: Data[] = [];
  public totalHighRecords: Data[] = [];
  public totalMediumRecords: Data[] = [];
  public totalLowRecords: Data[] = [];
  public dataFilter = new DataFilter();

  @Output() rowSelection = new EventEmitter<Data>();

  constructor(private processorService: ProcessingRequestService, private router: Router) { 
    this.totalGroupData = this.processorService.getRetrievedData();
    
    this.alldataRecords = this.accumulateData([], this.totalGroupData[0]);
    this.alldataRecords.sort((a,b) => a.dataId.localeCompare(b.dataId));

    this.alldataRecords.forEach( data => {
      if (data && data.dataSeverity === 'HIGH')  {
        this.totalHighRecords.push(data);
      } else if (data && data.dataSeverity === 'MEDIUM')  {
        this.totalMediumRecords.push(data);
      } else if (data && data.dataSeverity === 'LOW')  {
        this.totalLowRecords.push(data);
      }
    })
    console.log(this.totalGroupData[0].highlights);
    console.log(this.totalGroupData[0].checkpoints);
  }

  ngOnInit(): void {
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

  goToSeverityData(data: Data) {
    console.log('Data selected '+ data);
    this.deActivateAllGroups(this.totalGroupData[0]);
    this.totalGroupData[0].active=true;
    this.totalGroupData[0].expanded=true;
    this.processorService.setSelectedGroup(this.totalGroupData[0]);
    this.processorService.setSelectedData(data);
  }

  deActivateAllGroups(groupSet: Group) {
    groupSet.active=false;
    for (let index = 0; index < groupSet.subGroups.length; index++) {
      const element = groupSet.subGroups[index];
      this.deActivateAllGroups(element);
    }
  }

  pin(dataRecord: Data) {
    if (dataRecord.pinned) {
      dataRecord.pinned = false;
    } else {
      dataRecord.pinned = true;
    }
  }
}
