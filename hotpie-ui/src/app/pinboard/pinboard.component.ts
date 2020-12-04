import { Component, OnInit } from '@angular/core';
import { Data } from '../model/data.model';
import { ProcessingRequestService } from '../service/processing.request.service';
import { Group } from '../model/group.model';

@Component({
  selector: 'app-pinboard',
  templateUrl: './pinboard.component.html',
  styleUrls: ['./pinboard.component.scss']
})
export class PinboardComponent implements OnInit {

  constructor(private processorService: ProcessingRequestService) { }

  pinnedRecords: Data[] = [];
  public totalGroupData: Group[];
  public totalRecords: Data[] = [];

  ngOnInit(): void {
    console.log('Initializing the pin board.');
    this.totalGroupData = this.processorService.getRetrievedData();
    this.totalRecords = this.accumulateData([], this.totalGroupData[0]);
    this.totalRecords.forEach(data => {
      if (data.pinned) {
        this.pinnedRecords.push(data);
      }
    });
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

  unpinData(data: Data) {
    const index: number = this.pinnedRecords.indexOf(data);
    if (index !== -1) {
        this.pinnedRecords.splice(index, 1);
    }   
  }
}
