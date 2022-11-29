import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';
import { CryptoConfigDeleteService } from './crypto-config-delete.service';

@Component({
  selector: 'app-cryptoconfig-delete',
  templateUrl: './crypto-config-delete.component.html',
  styleUrls: ['./crypto-config-delete.component.css']
})
export class CryptoConfigDeleteComponent implements OnInit {

  filename:string ="";
  id:number = 0;

  constructor(private service: CryptoConfigDeleteService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get("id"));
  }

  onDelete(){
    this.service.deleteById(this.id).subscribe(response => {
        this.filename = response;
    })
  }

  downloadFile(){
    this.service.downloadFile(this.filename).subscribe(file => saveAs(file,this.filename));
  }

}
