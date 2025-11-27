import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  submitting = false;
  feedback = '';
  hasError = false;

  loginForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  constructor(private fb: FormBuilder, private api: ApiService) {}

  submit(): void {
    if (this.loginForm.invalid || this.submitting) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.submitting = true;
    this.feedback = '';
    this.hasError = false;
    const credentials = this.loginForm.getRawValue() as { username: string; password: string };

    this.api.login(credentials).subscribe({
      next: authenticated => {
        this.submitting = false;
        this.hasError = !authenticated;
        this.feedback = authenticated
          ? 'Credentials accepted. You are signed in.'
          : 'Login denied. Please check your username or password.';
      },
      error: error => {
        this.submitting = false;
        this.hasError = true;
        const backendMessage = error?.error ?? '';
        this.feedback = backendMessage
          ? backendMessage
          : 'Login could not be completed. Please try again.';
      }
    });
  }
}
