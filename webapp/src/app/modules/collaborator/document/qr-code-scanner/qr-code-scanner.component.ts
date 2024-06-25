import { Component, OnInit, ViewChild } from '@angular/core';
import { NbDialogRef } from '@nebular/theme';
import { Result } from '@zxing/library';
import { ZXingScannerComponent } from '@zxing/ngx-scanner';
import { ToastrStatusEnum } from 'src/app/enumerations/toastr-status.enum';
import { ToastrService } from 'src/app/services/toastr.service';

@Component({
  selector: 'app-qr-code-scanner',
  templateUrl: './qr-code-scanner.component.html',
  styleUrls: ['./qr-code-scanner.component.scss']
})
export class QrCodeScannerComponent implements OnInit {
  @ViewChild('scanner', {static: true}) scanner: ZXingScannerComponent;

  hasDevices: boolean;
  hasPermission: boolean;
  qrResult: Result;
  availableDevices: MediaDeviceInfo[];
  currentDevice: MediaDeviceInfo = null;

  constructor( private toastrService: ToastrService,  
    private dialogRef: NbDialogRef<QrCodeScannerComponent>) { }

  ngOnInit(): void {
    this.scannerInit();
   
  }
  scannerInit() {

    this.scanner.camerasFound.subscribe((devices: MediaDeviceInfo[]) => {
      this.hasDevices = true;
      this.availableDevices = devices;
      this.currentDevice = null;

     // this.compareWith = this.compareWithFn;
      this.onDeviceSelectChange(devices[devices.length - 1].deviceId);
    });

    this.scanner.camerasNotFound.subscribe(() => this.hasDevices = false);

    this.scanner.scanComplete.subscribe((result: Result) => this.qrResult = result);
    this.scanner.permissionResponse.subscribe((perm: boolean) => this.hasPermission = perm);
  }

  displayCameras(cameras: MediaDeviceInfo[]) {
   this.availableDevices = cameras;
 }

 handleQrCodeResult(resultString: string) {
  console.log("reslt from QR code",resultString);
  this.dialogRef.close(resultString);
 }

 onDeviceSelectChange(selected: string) {

 for (const device of this.availableDevices) {
     if (device.deviceId === selected) {
       this.currentDevice = device;
 
     }
   }
 }
 onCancelDialog() {
  this.dialogRef.close("false");

}
}
