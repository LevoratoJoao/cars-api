import { TestBed } from '@angular/core/testing';

import { ServicesCarService } from './services.car.service';

describe('ServicesCarService', () => {
  let service: ServicesCarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicesCarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
