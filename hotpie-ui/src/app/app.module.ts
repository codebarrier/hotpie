import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClarityModule } from "@clr/angular";
import { ProcessorComponent } from './processor/processor.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProcessingRequestService } from './service/processing.request.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DataviewerComponent } from './dataviewer/dataviewer.component';
import { SeverityreportComponent } from './severityreport/severityreport.component';
import { DatareportComponent } from './datareport/datareport.component';
import { DataTimelineComponent } from './data-timeline/data-timeline.component';
import { SeverityAlertComponent } from './severity-alert/severity-alert.component';
import { DataNavigatorComponent } from './data-navigator/data-navigator.component';
import { DocumentationComponent } from './documentation/documentation.component';
import { ProfileConfigComponent } from './profile-config/profile-config.component';
import { ContactusComponent } from './contactus/contactus.component';
import { CreateprofileComponent } from './createprofile/createprofile.component';
import { RegextesterComponent } from './regextester/regextester.component'
import { RegexTestService } from './service/regex.service';
import { DocumentationOverviewComponent } from './documentation-overview/documentation-overview.component';
import { DocumentationLineComponent } from './documentation-line/documentation-line.component';
import { DocumentationGroupComponent } from './documentation-group/documentation-group.component';
import { DocumentationSeverityComponent } from './documentation-severity/documentation-severity.component';
import { DocumentationHighlightComponent } from './documentation-highlight/documentation-highlight.component';
import { DocumentationFiltersComponent } from './documentation-filters/documentation-filters.component';
import { DocumentationCheckpointsComponent } from './documentation-checkpoints/documentation-checkpoints.component';
import { DocumentationPrivacyComponent } from './documentation-privacy/documentation-privacy.component';
import { ProfileService } from './service/profile.service';
import { PinboardComponent } from './pinboard/pinboard.component';
import { DatagridComponent } from './datagrid/datagrid.component';
import { HighlightPipe } from './service/highlight.directive';

@NgModule({
  declarations: [
    AppComponent,
    ProcessorComponent,
    DataviewerComponent,
    SeverityreportComponent,
    DatareportComponent,
    DataTimelineComponent,
    SeverityAlertComponent,
    DataNavigatorComponent,
    DocumentationComponent,
    ProfileConfigComponent,
    ContactusComponent,
    CreateprofileComponent,
    RegextesterComponent,
    DocumentationOverviewComponent,
    DocumentationLineComponent,
    DocumentationGroupComponent,
    DocumentationSeverityComponent,
    DocumentationHighlightComponent,
    DocumentationFiltersComponent,
    DocumentationCheckpointsComponent,
    DocumentationPrivacyComponent,
    PinboardComponent,
    DatagridComponent,
    HighlightPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    ProcessingRequestService,
    RegexTestService,
    HttpClient,
    ProfileService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
