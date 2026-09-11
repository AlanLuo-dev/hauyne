import { provideZoneChangeDetection } from "@angular/core";
import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { provideNzDateFnsAdapter } from 'ng-zorro-antd/core/time';


bootstrapApplication(AppComponent, {...appConfig, providers: [provideZoneChangeDetection(), ...appConfig.providers, provideNzDateFnsAdapter()]})
  .catch((err) => console.error(err));
