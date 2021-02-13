import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JobVisitService } from 'app/entities/job-visit/job-visit.service';
import { IJobVisit, JobVisit } from 'app/shared/model/job-visit.model';

describe('Service Tests', () => {
  describe('JobVisit Service', () => {
    let injector: TestBed;
    let service: JobVisitService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobVisit;
    let expectedResult: IJobVisit | IJobVisit[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobVisitService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new JobVisit(0, currentDate, currentDate, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            arrived: currentDate.format(DATE_TIME_FORMAT),
            departed: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a JobVisit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            arrived: currentDate.format(DATE_TIME_FORMAT),
            departed: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            arrived: currentDate,
            departed: currentDate,
          },
          returnedFromService
        );

        service.create(new JobVisit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobVisit', () => {
        const returnedFromService = Object.assign(
          {
            arrived: currentDate.format(DATE_TIME_FORMAT),
            departed: currentDate.format(DATE_TIME_FORMAT),
            charge: 1,
            workCarriedOut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            arrived: currentDate,
            departed: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of JobVisit', () => {
        const returnedFromService = Object.assign(
          {
            arrived: currentDate.format(DATE_TIME_FORMAT),
            departed: currentDate.format(DATE_TIME_FORMAT),
            charge: 1,
            workCarriedOut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            arrived: currentDate,
            departed: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a JobVisit', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
