package io.getarrays.contactapi.resource;

import io.getarrays.contactapi.domain.Contact;
import io.getarrays.contactapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        return ResponseEntity.ok().body(contactService.getContact(id));
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return ResponseEntity.ok().body(contactService.createContact(contact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactService.deleteContact(contactService.getContact(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/photo/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadPhoto(@PathVariable String id,
                                                           @RequestParam("file") MultipartFile file) {
        String photoUrl = contactService.uploadPhoto(id, file);
        return ResponseEntity.ok().body(Map.of("url", photoUrl));
    }

    // Méthode pour servir les images depuis le serveur
    // Note: Vous devriez implémenter cette méthode dans votre ContactService
    // pour renvoyer les données de l'image depuis le répertoire PHOTO_DIRECTORY
    @GetMapping(path = "/image/{filename}", produces = IMAGE_JPEG_VALUE)
    public byte[] getPhoto(@PathVariable String filename) {
        // Implémentation à ajouter dans votre ContactService
        // return contactService.getPhoto(filename);
        throw new UnsupportedOperationException("Not implemented yet");
    }
}