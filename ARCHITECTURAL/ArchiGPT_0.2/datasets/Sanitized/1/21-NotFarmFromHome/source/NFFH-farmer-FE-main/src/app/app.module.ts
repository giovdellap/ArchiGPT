import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './utils/material.module';
import { ApiService } from './services/api.service';
import { UserService } from './services/user.service';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { LoginpageComponent } from './pages/loginpage/loginpage.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ProductsComponent } from './pages/products/products.component';
import { AddComponent } from './pages/products/components/add/add.component';
import { ModifyComponent } from './pages/products/components/modify/modify.component';
import { SelectComponent } from './pages/products/components/select/select.component';
import { RegistrationpageComponent } from './pages/registrationpage/registrationpage.component';



@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    LoginpageComponent,
    ProductsComponent,
    AddComponent,
    ModifyComponent,
    SelectComponent,
    RegistrationpageComponent,
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    ApiService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
