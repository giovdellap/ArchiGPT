import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbCarouselModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CookieService } from 'ngx-cookie-service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CartService } from './cart.service';
import { APIService } from './connections/api.service';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { OrderPageComponent } from './pages/order-page/order-page.component';
import { PersonalAreaComponent } from './pages/personal-area/personal-area.component';
import { RegistrationPageComponent } from './pages/registration-page/registration-page.component';
import { StorePageComponent } from './pages/store-page/store-page.component';
import { StoresListPageComponent } from './pages/stores-list-page/stores-list-page.component';
import { UserService } from './user.service';
import { MaterialModule } from './utils/material.module';


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    RegistrationPageComponent,
    HomepageComponent,
    StoresListPageComponent,
    StorePageComponent,
    CartPageComponent,
    OrderPageComponent,
    PersonalAreaComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    NgbModule,
    NgbCarouselModule
  ],
  providers: [
    APIService,
    CartService,
    UserService,
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
