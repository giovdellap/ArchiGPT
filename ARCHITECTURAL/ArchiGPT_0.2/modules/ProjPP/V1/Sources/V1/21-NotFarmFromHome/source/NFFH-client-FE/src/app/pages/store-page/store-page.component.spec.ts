import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StorePageComponent } from './store-page.component';

describe('StorePageComponent', () => {
  let component: StorePageComponent;
  let fixture: ComponentFixture<StorePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StorePageComponent]
    });
    fixture = TestBed.createComponent(StorePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
