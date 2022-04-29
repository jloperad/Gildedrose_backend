import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'delete-dialog',
  templateUrl: 'delete-dialog.component.html',
  styleUrls: ['delete-dialog.component.css'],
})
export class DeleteDialogComponent implements OnInit {
  name: string = '';

  constructor(
    private dialogRef: MatDialogRef<DeleteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: { name: string }
  ) {}

  ngOnInit(): void {
    this.name = this.data.name;
  }

  onDelete(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
