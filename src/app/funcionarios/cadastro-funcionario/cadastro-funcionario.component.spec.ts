import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroFuncionarioComponent } from './cadastro-funcionario.component';

describe('CadastroFuncionarioComponent', () => {
  let component: CadastroFuncionarioComponent;
  let fixture: ComponentFixture<CadastroFuncionarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadastroFuncionarioComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CadastroFuncionarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
