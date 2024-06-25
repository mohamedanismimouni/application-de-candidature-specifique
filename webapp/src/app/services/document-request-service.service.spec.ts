import { TestBed } from '@angular/core/testing';

import { DocumentRequestServiceService } from './document-request-service.service';

describe('DocumentRequestServiceService', () => {
  let service: DocumentRequestServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentRequestServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
