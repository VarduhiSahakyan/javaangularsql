import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { saveAs } from 'file-saver';
import { ProfileDeleteServiceService } from './profile-delete-service.service';

@Component({
  selector: 'app-profile-delete',
  templateUrl: './profile-delete.component.html',
  styleUrls: ['./profile-delete.component.css']
})
export class ProfileDeleteComponent implements OnInit {

  profileId: number = 0;
  filename: string = "";

  constructor(
    private service: ProfileDeleteServiceService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.profileId = Number(this.route.snapshot.paramMap.get('profileId'));
  }

  deleteProfile() {
    this.service.delete(this.profileId).subscribe(response => {
      this.filename = response;
    })
  }

  downloadFile() {
    this.service.downloadSqlFile(this.filename).subscribe(
      file => saveAs(file, this.filename)
      );
  }
}
