export class TextRequest {
    currentRoom: number;
    message: string;

    constructor(currentRoom: number, message: string) {
        this.currentRoom = currentRoom;
        this.message = message;
    }
}
