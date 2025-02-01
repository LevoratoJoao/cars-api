import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-pagination',
  imports: [CommonModule, FormsModule],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})
export class PaginationComponent {
  @Input() currentPage!: number;
  @Input() sizeOfPage!: number;
  @Input() totalItems!: number;
  @Output() pageChanged: EventEmitter<number> = new EventEmitter();


  get getTotalPages(): number {
    return Math.floor(this.totalItems / this.sizeOfPage) + 1;
  }

  // changePage(page: number): void {
  //   console.log("Ta trocando para " + page);
  //   if (page >= 1 && page <= this.getTotalPages) {
  //     this.currentPage = page;
  //     this.pageChanged.emit(page);
  //   }
  // }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.pageChanged.emit(this.currentPage);
    }
  }

  nextPage() {
    if (this.currentPage < this.getTotalPages) {
      this.currentPage++;
      this.pageChanged.emit(this.currentPage);
    }
  }

  get getPageNumbers(): number[] {
    // return Array.from(
    //   {
    //     length: this.totalPages
    //   },
    //   (_, index) => index + 1
    // );
    let pageNumbers = Array(this.getTotalPages).fill(0).map((x, i) => i + 1);
    return pageNumbers;
  }

  goToPage(pageNumber: number): void {
    // const pageNumber = parseInt(page, 10);
    // if (pageNumber && pageNumber >= 1 && pageNumber <= this.getTotalPages && pageNumber !== this.currentPage) {

      this.currentPage = pageNumber;
      this.pageChanged.emit(this.currentPage);
    // }
  }
}
