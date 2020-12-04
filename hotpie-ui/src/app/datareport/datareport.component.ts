import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { Group } from '../model/group.model';
import { Data } from '../model/data.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ProcessingRequestService } from '../service/processing.request.service';
import { ClrDatagridStringFilterInterface, ClrDatagrid, ClrDatagridPagination } from '@clr/angular';


class DataFilter implements ClrDatagridStringFilterInterface<Data> {

  public searchString: string = '';

  accepts(data: Data, search: string): boolean {
    return data.data.toLowerCase().search(search.toLowerCase()) > 0;
  }

  filter(data: Data): boolean {
    return this.searchString === null || this.searchString === '' || this.searchString === undefined || data.data.toLowerCase().search(this.searchString.toLowerCase()) > 0;
  }
}

@Component({
  selector: 'app-datareport',
  templateUrl: './datareport.component.html',
  styleUrls: ['./datareport.component.scss']
})
export class DatareportComponent implements OnInit, AfterViewInit {

  public totalGroupData: Group[];
  public detailData: Data = null;
  public currentPage: number = 1;
  public currentPageSize: number = 10;
  public dataFilter = new DataFilter();
  public selectedGroup: Group;
  public navigationSubscription: any;
  public readyToShowData: boolean = false;

  private reportInitialized: boolean = false;
  constructor(private route: ActivatedRoute, private processorService: ProcessingRequestService, private router: Router) {

    this.route.params.subscribe(params => {
      if (!this.reportInitialized || params['dataId'] === 'changer') {
        return;
      }
      console.log('parameters have changed.');
      let data = this.processorService.getSelectedData();
      let group = this.processorService.getSelectedGroup();
      this.totalGroupData = this.processorService.getRetrievedData();
      if (data) {
        console.log('Found Data.')
        // this.selectedGroupForReport(this.totalGroupData[0]);
        this.selectedDataDetail(data);
      } else if (group != this.selectedGroup) {
        console.log('Found Group.')
        this.selectedGroupForReport(group);
      }
    });
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy() {
    if (this.navigationSubscription) {
      this.navigationSubscription.unsubscribe();
    }
  }

  public groupId: string;
  public totalRecords: Data[] = [];
  public selectedData: Data;
  public search: string;

  selectedGroupForReport(group: Group) {
    if (group) {
      this.selectedGroup = group;
      setTimeout(() => {
        this.detailData = null;
        this.currentPage = 1;
      });
      console.log('Selected data report group is ' + this.selectedGroup.groupName);
      this.totalRecords = [];
      let gatheredData = this.accumulateData([], this.selectedGroup);
      if (gatheredData && gatheredData.length > 0) {
        gatheredData.forEach(element => {
          if (element) {
            this.totalRecords.push(element);
          }
        });
      } else {
        this.totalRecords = null;
      }
      this.totalRecords.sort((a, b) => a.dataId.localeCompare(b.dataId));
    }
  }

  selectedDataDetail(data: Data) {
    if (data) {
      this.totalGroupData = this.processorService.getRetrievedData();
      this.totalRecords = this.accumulateData([], this.totalGroupData[0]);
      // this.goToRow(data);
      this.onRowSelection(data);
    } else {
      this.detailData = null;
    }
  }

  ngOnInit(): void {
    console.log('Init again');
    this.readyToShowData = false;
    this.reportInitialized = true;
    this.totalGroupData = this.processorService.getRetrievedData();
    this.totalRecords = this.accumulateData([], this.totalGroupData[0]);
    this.totalRecords.sort((a, b) => a.dataId.localeCompare(b.dataId));
    this.selectedGroup = this.totalGroupData[0];
    let group = this.processorService.getSelectedGroup();
    this.selectedGroupForReport(group);
    let data = this.processorService.getSelectedData();
    this.selectedDataDetail(data);
    this.readyToShowData = true;
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

  onRowSelection(selectedRow: Data) {
    this.selectedGroupForReport(this.totalGroupData[0]);
    this.selectedGroup = this.totalGroupData[0];
    this.goToRow(selectedRow);
  }

  pin(dataRecord: Data) {
    if (dataRecord.pinned) {
      dataRecord.pinned = false;
    } else {
      dataRecord.pinned = true;
    }
  }

  @ViewChild(ClrDatagridPagination) page: ClrDatagridPagination;

  goToRow(highlightedData: Data) {
    let filteredRecords: Data[] = [];
    for (let index = 0; index < this.totalRecords.length; index++) {
      if (this.dataFilter.filter(this.totalRecords[index])) {
        filteredRecords.push(this.totalRecords[index]);
      }
    }
    
    for (let index = 0; index < filteredRecords.length; index++) {
      const record: Data = filteredRecords[index];
      // console.log('Checking ' + record.dataId + ' vs ' + highlightedString.data.dataId);
      if (record.dataId === highlightedData.dataId) {
        setTimeout(() => {
          this.detailData = record;
          this.currentPage = Math.floor(index / this.currentPageSize) + 1;
          console.log('Found the record on page ' + this.currentPage);
        });
        return;
      }
    }
  }

  findGroup(groupId: String, group: Group): Group {

    if (group.groupId === groupId) {
      return group;
    } else {
      if (group.subGroups && group.subGroups.length > 0) {
        for (let index = 0; index < group.subGroups.length; index++) {
          const grp = group.subGroups[index];
          let searchedGroup = this.findGroup(groupId, grp);
          if (searchedGroup && searchedGroup.groupId === groupId) {
            return searchedGroup;
          }
        }
      }
    }
    return;
  }

  currentIndex: number = -1;
  searchEmpty: boolean = false;
  searchStarted: boolean = false;
  onSearch() {
    this.detailData = null;
    this.searchStarted = true;
    this.currentIndex = -1;
    this.currentPage = 1;

    for (let index = 0; index < this.totalRecords.length; index++) {
      const data = this.totalRecords[index];
      if (this.dataFilter.filter(data) && data.data.toLowerCase().search(this.search.toLowerCase()) > 0) {
        console.log('Going to row ' + index);
        this.goToRow(data);
        this.currentIndex = index;
        break;
      }
    }
    if (this.currentIndex === -1) {
      this.searchEmpty = true;
    } else {
      this.searchEmpty = false;
    }
  }

  onNextSearch() {
    for (let index = this.currentIndex + 1; index < this.totalRecords.length; index++) {
      const data = this.totalRecords[index];
      if (this.dataFilter.filter(data) && data.data.toLowerCase().search(this.search.toLowerCase()) > 0) {
        console.log('Going to row ' + data);
        this.goToRow(data);
        this.currentIndex = index;
        break;
      }
    }
  }

  onPrevSearch() {
    for (let index = this.currentIndex - 1; index >= 0; index--) {
      const data = this.totalRecords[index];
      if (this.dataFilter.filter(data) && data.data.toLowerCase().search(this.search.toLowerCase()) > 0) {
        console.log('Going to row ' + data);
        this.goToRow(data);
        this.currentIndex = index;
        break;
      }
    }
  }

  onClearSearch() {
    this.search = null;
    this.currentIndex = -1;
    this.detailData = null;
    this.currentPage = 1;
    this.searchStarted = false;
  }

  refresh(state) {
    this.currentPageSize = state.page.size;
  }
}
