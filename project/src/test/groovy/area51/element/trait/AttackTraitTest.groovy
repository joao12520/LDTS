package area51.element.trait

import area51.element.trait.weapon.Bite
import area51.element.trait.weapon.MeleeWeapon
import area51.element.trait.weapon.RangeWeapon
import area51.element.trait.weapon.SpitAcid
import spock.lang.Specification

class AttackTraitTest extends Specification {
    def "has Damage per Attack"() {
        given:
        def attack = new AttackTrait(new Bite())

        expect:
        attack.getDamagePerAttack() == 30
    }

    def "has and sets Attack Direction"() {
        given:
        def attack = new AttackTrait(new Bite())
        def direction = Direction.RIGHT

        when:
        attack.setAttackDirection(direction)

        then:
        attack.getAttackDirection() == direction
    }

    def "get Weapon Class"() {
        given:
        def attack = new AttackTrait(new Bite())

        expect:
        attack.getWeaponClass() == MeleeWeapon.class
    }

    def "add and selectNextWeapon Weapon"(){
        given:
        def attack = new AttackTrait(new Bite())
        def weapon = new SpitAcid()

        when:
        attack.addWeapon(weapon)
        attack.selectNextWeapon()

        then:
        attack.getWeaponClass() == RangeWeapon.class
    }

    def "dont add repeated Weapon"(){
        given:
        def attack = new AttackTrait(new Bite())
        def weapon = new SpitAcid()
        def weapon2 = new SpitAcid()

        when:
        attack.addWeapon(weapon)
        attack.addWeapon(weapon2)
        attack.selectNextWeapon()
        attack.selectNextWeapon()

        then:
        attack.getWeaponClass() == MeleeWeapon.class
    }

    def "remove Weapon"(){
        given:
        def attack = new AttackTrait(new Bite())
        def weapon = new SpitAcid()

        when:
        attack.addWeapon(weapon)
        attack.removeWeapon(SpitAcid.class)
        attack.selectNextWeapon()

        then:
        attack.getWeaponClass() == MeleeWeapon.class
    }

    def "does not remove if only one Weapon left"(){
        given:
        def weapon = new Bite()
        def attack = new AttackTrait(weapon)

        when:
        attack.removeWeapon(weapon)

        then:
        attack.getWeaponClass() == MeleeWeapon.class
    }

    def "get Weapon Class Name"(){
        given:
        def weapon = new SpitAcid()
        def attack = new AttackTrait(weapon)

        expect:
        attack.getWeaponName() == "SpitAcid"
    }
}