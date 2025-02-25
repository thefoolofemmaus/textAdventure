export class TextResponse {
    inRoom: number;
    message: string;

    constructor(currentRoom: number, message: string) {
        this.inRoom = currentRoom;
        this.message = message;
    }
}
