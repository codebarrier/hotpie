import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DataviewerComponent } from './dataviewer/dataviewer.component';
import { DatareportComponent } from './datareport/datareport.component';
import { SeverityreportComponent } from './severityreport/severityreport.component';
import { ProcessorComponent } from './processor/processor.component';
import { DocumentationComponent } from './documentation/documentation.component';
import { ProfileConfigComponent } from './profile-config/profile-config.component';
import { ContactusComponent } from './contactus/contactus.component';
import { DocumentationOverviewComponent } from './documentation-overview/documentation-overview.component';
import { DocumentationLineComponent } from './documentation-line/documentation-line.component';
import { DocumentationGroupComponent } from './documentation-group/documentation-group.component';
import { DocumentationSeverityComponent } from './documentation-severity/documentation-severity.component';
import { DocumentationHighlightComponent } from './documentation-highlight/documentation-highlight.component';
import { DocumentationFiltersComponent } from './documentation-filters/documentation-filters.component';
import { DocumentationPrivacyComponent } from './documentation-privacy/documentation-privacy.component';
import { DocumentationCheckpointsComponent } from './documentation-checkpoints/documentation-checkpoints.component';
import { PinboardComponent } from './pinboard/pinboard.component';


const routes: Routes = [
  {path: '', component: ProcessorComponent, pathMatch: 'full'},
    {path: 'processedData', component: DataviewerComponent, children:[
      {path: 'datareport/:groupId/:dataId', component: DatareportComponent, pathMatch: 'prefix'},
      {path: 'severityreport', component: SeverityreportComponent},
      {path: 'pinboard', component: PinboardComponent},
    ], runGuardsAndResolvers: 'always'},
  {path: 'profile', component: ProfileConfigComponent, pathMatch: 'full'},
  {path: 'documentation', component: DocumentationComponent, children:[
    {path: 'overview', component: DocumentationOverviewComponent},
    {path: 'linedocument', component: DocumentationLineComponent},
    {path: 'groups', component: DocumentationGroupComponent},
    {path: 'severity', component: DocumentationSeverityComponent},
    {path: 'highlights', component: DocumentationHighlightComponent},
    {path: 'filters', component: DocumentationFiltersComponent},
    {path: 'checkpoints', component: DocumentationCheckpointsComponent},
    {path: 'privacy', component: DocumentationPrivacyComponent},
  ]},
  {path: 'contact', component: ContactusComponent, pathMatch: 'full'},
  {path: '404',  component: ProcessorComponent },
  {path: '*', redirectTo: "", pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
