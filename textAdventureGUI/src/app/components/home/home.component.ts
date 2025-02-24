import { Component, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: false,
})
export class HomeComponent {
  messages: { text: string, sender: string }[] = [];
  newMessage: string = '';

  @ViewChild('chatContainer') chatContainer!: ElementRef;

  sendMessage(sentBy: string) {
    if (this.newMessage.trim()) {
      this.messages.push({ text: this.newMessage, sender: sentBy });
      this.newMessage = '';
      setTimeout(() => this.scrollToBottom(), 100)
    }
  }

  scrollToBottom() {
    if (this.chatContainer) {
      this.chatContainer.nativeElement.scrolltop = this.chatContainer.nativeElement.scrollHeight;
    }
  }

}
