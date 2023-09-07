package pl.jacekk.phishingdetector.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocked_messages")
@NoArgsConstructor
public class BlockedMessageEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String sender;
    @NotBlank

    private String content;
    @ManyToOne
    @JoinColumn(name="contract_id", nullable=false)
    private ContractEntity contract;
    @ManyToOne
    @JoinColumn(name="link_id", nullable=false)
    private LinkEntity link;

    public BlockedMessageEntity(String sender, String content, ContractEntity contract, LinkEntity link) {
        this.sender = sender;
        this.content = content;
        this.contract = contract;
        this.link = link;
    }
}
