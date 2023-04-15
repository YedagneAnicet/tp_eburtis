import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Personne } from 'src/app/models/personne';
import { PersonneService } from 'src/app/services/personne.service';

@Component({
  selector: 'app-liste-personne',
  templateUrl: './liste-personne.component.html',
  styleUrls: ['./liste-personne.component.scss'],
})
export class ListePersonneComponent implements OnInit {
  first = 0; // attribuer lié à la pagination
  rows = 10; // attribuer lié à la pagination

  visible!: boolean;

  submitted!: boolean;
  personne!: Personne;

  formpersonne!: FormGroup;

  listePersonne : any;

  constructor(private _personneService: PersonneService) {}

  ngOnInit() {
    this.getListePersonne()
  }

  showDialog() {
    this.visible = true;
  }

  getListePersonne() {
    this._personneService.getAllPersonne().subscribe({
      next: (reponse: any) => {
        this.listePersonne = reponse;
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }

  deletePersonne(id: number) {
    this._personneService.deletePersonne(id).subscribe({
      next: (reponse: any) => {
        // Si la suppression a réussi, on met à jour la liste des personnes
        this.getListePersonne();
      },
      error: (error: any) => {
        console.log(error);
      },
    });
  }

}
