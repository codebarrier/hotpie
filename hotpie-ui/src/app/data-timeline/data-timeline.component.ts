import { Component, OnInit, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { Group } from '../model/group.model';
import { ProcessingRequestService } from '../service/processing.request.service';
import { Checkpoint } from '../model/checkpoint.model';
import { Data } from '@angular/router';

@Component({
  selector: 'app-data-timeline',
  templateUrl: './data-timeline.component.html',
  styleUrls: ['./data-timeline.component.scss']
})
export class DataTimelineComponent implements OnInit, AfterViewInit {

  totalGroupData: Group[];
  @Output() rowSelection = new EventEmitter<Data>();

  constructor(private processorService: ProcessingRequestService) { 
    this.totalGroupData = this.processorService.getRetrievedData();
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.panelOpen = true;
    });
  }

  checkpointSuccess: boolean = true;
  panelOpen: boolean = false;

  ngOnInit(): void {
    this.totalGroupData[0].checkpoints.forEach((checkpoint) => {
      if (checkpoint && (checkpoint.checkpointReached.toString() === 'false')) {
        this.checkpointSuccess = false;
        return;
      }
    });
  }

  goToCheckpointRow(checkpoint: Checkpoint) {
    this.rowSelection.emit(checkpoint.checkpointData);
  }

}
