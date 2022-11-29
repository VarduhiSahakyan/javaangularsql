import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';
import { ProfilesetDeleteService } from './profileset-delete.service';

@Component({
  selector: 'app-profileset-delete',
  templateUrl: './profile-set-delete.component.html',
  styleUrls: ['./profile-set-delete.component.css']
})
export class ProfileSetDeleteComponent implements OnInit {

  id: number = 0;
  filename:string = "";

  constructor(
    private profileSetDeleteService: ProfilesetDeleteService,
    private route: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get("id"));
  }

  delete(){
    this.profileSetDeleteService.deleteProfileSet(this.id).subscribe(response => {
      this.filename = response
    });
  }

  downloadFile(){
     this.profileSetDeleteService.downloadSqlFile(this.filename).subscribe(file => saveAs(file,this.filename));
  }
}
