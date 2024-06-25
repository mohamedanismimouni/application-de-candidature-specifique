import { TestBed } from '@angular/core/testing';

import { UsefulDocumentService } from './useful-document.service';

describe('UsefulDocumentService', () => {
  let service: UsefulDocumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsefulDocumentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
