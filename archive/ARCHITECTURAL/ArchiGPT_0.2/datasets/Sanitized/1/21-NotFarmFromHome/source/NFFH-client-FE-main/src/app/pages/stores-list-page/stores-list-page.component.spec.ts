import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoresListPageComponent } from './stores-list-page.component';

describe('StoresListPageComponent', () => {
  let component: StoresListPageComponent;
  let fixture: ComponentFixture<StoresListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StoresListPageComponent]
    });
    fixture = TestBed.createComponent(StoresListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
