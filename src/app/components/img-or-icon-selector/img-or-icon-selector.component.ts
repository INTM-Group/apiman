import {AfterViewInit, ChangeDetectorRef, Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {ConfigService} from "../../services/config/config.service";

@Component({
  selector: 'app-img-or-icon-selector',
  templateUrl: './img-or-icon-selector.component.html',
  styleUrls: ['./img-or-icon-selector.component.scss']
})
export class ImgOrIconSelectorComponent implements AfterViewInit, OnChanges {
  @Input() imgUrl?: string;
  @Input() dimension?: string;

  constructor(public configService: ConfigService, private cdr: ChangeDetectorRef) { }

  ngOnChanges(changes: SimpleChanges) {
    this.imgUrl = changes.imgUrl ? changes.imgUrl.currentValue : ''
    this.cdr.detectChanges();
    this.resize();
  }

  ngAfterViewInit(): void {
    if (this.dimension)
      this.resize()
  }

  private resize() {
    if (this.imgUrl) {
      const images = Array.from(document.getElementsByClassName('api-img') as HTMLCollectionOf<HTMLElement>)

      images.forEach((img: HTMLElement) => {
        img.style.width = this.dimension + 'px';
        img.style.height = this.dimension + 'px'
      });
    }else{
      const icons = Array.from(document.getElementsByClassName('api-icon') as HTMLCollectionOf<HTMLElement>)

      icons.forEach((icon: HTMLElement) => {
        icon.style.fontSize = this.dimension + 'px';
        icon.style.height = this.dimension + 'px'
      });
    }
  }
}
