import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-contactus',
  templateUrl: './contactus.component.html',
  styleUrls: ['./contactus.component.scss']
})
export class ContactusComponent implements OnInit, AfterViewInit {

  constructor() { }

  ngAfterViewInit(): void {
    (<any>window).twttr.widgets.load();
  }

  ngOnInit(): void {
  }

}
