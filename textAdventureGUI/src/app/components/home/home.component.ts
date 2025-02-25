import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { TextService } from '../../services/text.service';
import { TextRequest } from '../../models/text-request.model';
import { TextResponse } from '../../models/text-response.model'

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  standalone: false,
})
export class HomeComponent {
  messages: { text: string, sender: string }[] = [];
  newMessage: string = '';
  textService = inject(TextService);
  currentRoom = 1;
  response: TextResponse | null = null;

  @ViewChild('chatContainer') chatContainer!: ElementRef;

  sendMessage(sentBy: string) {
    if (this.newMessage.trim()) {
      this.messages.push({ text: this.newMessage, sender: sentBy });
      
      setTimeout(() => this.scrollToBottom(), 100);
      const textData: TextRequest = { currentRoom: this.currentRoom, message: this.newMessage };
      this.textService.sendText(textData).subscribe({
        next: (res) => this.processResponse(res),
        error: (err) => console.error('Error:', err)
      });
      this.newMessage = '';
    }
  }

  processResponse(response: TextResponse) {
    this.messages.push({ text: response.message, sender: 'Me' });
    this.currentRoom = response.inRoom;
    console.log("Now in Room" + this.currentRoom);
    setTimeout(() => this.scrollToBottom(), 100);
  }

  scrollToBottom() {
    if (this.chatContainer) {
      this.chatContainer.nativeElement.scrolltop = this.chatContainer.nativeElement.scrollHeight;
    }
  }

}
