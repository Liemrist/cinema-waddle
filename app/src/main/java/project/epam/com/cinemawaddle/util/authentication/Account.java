package project.epam.com.cinemawaddle.util.authentication;


public final class Account {
    private final int id;
    private final Avatar avatar;
    private final String name;
    private final String username;
    // TODO: add adult field and handle it's usage.

    public Account(int id, Avatar avatar, String name, String username) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getGravatarHash() {
        return avatar.gravatar.hash;
    }


    private final class Avatar {
        private final Gravatar gravatar;

        private Avatar(Gravatar gravatar) {
            this.gravatar = gravatar;
        }

        private final class Gravatar {
            private final String hash;

            private Gravatar() {
                hash = null;
            }
        }
    }
}
