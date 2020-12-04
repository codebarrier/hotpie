import { Component, OnInit, Input } from '@angular/core';
import { RegexTestService } from '../service/regex.service';
import { RegexTestResult } from '../model/regextestresult.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-regextester',
  templateUrl: './regextester.component.html',
  styleUrls: ['./regextester.component.scss']
})
export class RegextesterComponent implements OnInit {

  constructor(private regexTest: RegexTestService) { }

  sampleData: string;

  @Input()
  regex: string;

  @Input()
  groupMatch: string;

  matchSuccess: boolean;
  matchMessage: string;


  public result: RegexTestResult;

  ngOnInit(): void {
  }

  testRegex() {
    let response  = <Observable<RegexTestResult>>this.regexTest.testRegex(this.regex, this.sampleData);
    console.log('Is group match = ' + this.groupMatch);
    response.subscribe(testResult => {
      this.matchSuccess = false;
      this.matchMessage = '';
      this.result = testResult;
      if (this.result.matchSuccessful) {
        if (this.groupMatch === 'true' && this.result.matchedGroups && this.result.matchedGroups.length > 0) {
          this.matchSuccess = true;
          this.matchMessage = 'Match Successful.';
        } else if (this.groupMatch === 'false') {
          this.matchSuccess = true;
          this.matchMessage = 'Match Successful.';
        } else {
          this.matchSuccess = false;
          this.matchMessage = '\"Capturing Groups\" not found in the regex.';
        }
      } else {
        this.matchSuccess = false;
        this.matchMessage = 'Match Failed! Please make sure your regex is valid and is a \"full match\" for the data.';
      }
      console.log(testResult);
    });
  }

}
