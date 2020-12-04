import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Data } from '../model/data.model';
import { ClrDatagridStringFilterInterface } from '@clr/angular';

class DataFilter implements ClrDatagridStringFilterInterface<Data> {

  public searchString: string = '';

  accepts(data: Data, search: string):boolean {
    return data.data.toLowerCase().search(search) > 0;
  }

  filter(data: Data): boolean {
    return this.searchString === null || this.searchString === '' || this.searchString === undefined || data.data.toLowerCase().search(this.searchString.toLowerCase()) > 0;
  }
}

@Component({
  selector: 'app-datagrid',
  templateUrl: './datagrid.component.html',
  styleUrls: ['./datagrid.component.scss']
})
export class DatagridComponent implements OnInit {

  public search: string;
  currentIndex: number = -1;
  searchEmpty: boolean = false;
  searchStarted: boolean = false;
  public detailData: Data = null;
  public currentPage: number = 1;
  public currentPageSize: number = 10;
  public dataFilter = new DataFilter();
  
  @Input("totalData")
  public totalRecords: Data[];

  constructor() { }

  ngOnInit(): void {
  }

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

  pin(dataRecord: Data) {
    if (dataRecord.pinned) {
      dataRecord.pinned = false;
      this.unpin.emit(dataRecord);
    } else {
      dataRecord.pinned = true;
    }
  }

  @Output() unpin = new EventEmitter<Data>();

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
}
