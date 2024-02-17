import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <layout>
      <router-outlet></router-outlet>
    </layout>
  `
})
export class AppComponent {
  title = 'poupa.online.app';
}
