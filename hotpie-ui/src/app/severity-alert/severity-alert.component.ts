import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { ProcessingRequestService } from '../service/processing.request.service';
import { Group } from '../model/group.model';
import { Data } from '@angular/router';

@Component({
  selector: 'app-severity-alert',
  templateUrl: './severity-alert.component.html',
  styleUrls: ['./severity-alert.component.scss']
})

export class SeverityAlertComponent implements OnInit {
  totalGroupData: Group[];
  @Output() showSeverityReport = new EventEmitter<boolean>();
  public showSeverityAlert: boolean = true;

  constructor(private processorService: ProcessingRequestService) {
    this.totalGroupData = this.processorService.getRetrievedData();
  }

  ngOnInit(): void {
  }

  triggerSeverityReport() {
    this.showSeverityReport.emit(true);
  }

  dismissSeverityAlert() {
    this.showSeverityAlert = false;
  }
}
