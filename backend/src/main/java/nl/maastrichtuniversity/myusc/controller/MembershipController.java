package nl.maastrichtuniversity.myusc.controller;


import nl.maastrichtuniversity.myusc.model.Membership;
import nl.maastrichtuniversity.myusc.dtos.MembershipDto;
import nl.maastrichtuniversity.myusc.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addMembership(@RequestBody MembershipDto membershipDto) {
       try {
           Membership returnedMembership = membershipService.createMembership(membershipDto);
           System.out.println("Membership created" + returnedMembership.toString());
           return ResponseEntity.ok().build();
       } catch (RuntimeException e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }
    }

    @DeleteMapping("/delete/{membershipId}/user/{userId}")
    public ResponseEntity<?> deleteMembership(@PathVariable Long membershipId, @PathVariable Long userId) {
        try {
            membershipService.deleteMembership(membershipId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
