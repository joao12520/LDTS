package area51.element.trait

import area51.element.trait.weapon.Bite
import area51.element.trait.weapon.MeleeWeapon
import spock.lang.Specification

class WeaponTest extends Specification {
    def "get Damage per Attack"() {
        given:
        def weapon = new Bite()

        expect:
        weapon.getDamagePerAttack() == 30
    }

    def "get Cycles per Attack"() {
        given:
        def weapon = new Bite()

        expect:
        weapon.getCyclesPerAttack() == 24
    }

    def "get Weapon Class"() {
        given:
        def weapon = new Bite()

        expect:
        weapon.getWeaponClass() == MeleeWeapon.class
    }

    def "get Weapon Name"() {
        given:
        def weapon = new Bite()

        expect:
        weapon.getWeaponName() == "Bite"
    }
}