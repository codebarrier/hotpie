import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'hotpie-ui';

  showBoard: boolean = false;
  scratchContent: string = '';

  showScratchPad() {
    if (this.showBoard === true) {
      this.showBoard = false;
    } else {
      this.showBoard = true;
    }
  }
}
