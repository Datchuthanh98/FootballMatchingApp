package com.example.myclub.util;

public class MapUtils {
//    public static String valueToStringOrNull(Map<String, Object> map, String key) {
//        if (map == null) return null;
//        Object value = map.get(key);
//        if (value == null) return null;
//        return (String) value;
//    }
//
//    public static boolean valueToBooleanOrFalse(Map<String, Object> map, String key) {
//        if (map == null) return false;
//        Object value = map.get(key);
//        if (value == null) return false;
//        if (value instanceof Boolean) return (boolean) value;
//        return false;
//    }
//
//    public static Integer valueToIntegerOrNull(Map<String, Object> map, String key) {
//        if (map == null) return null;
//        Object value = map.get(key);
//        if (value == null) return null;
//        if (value instanceof Integer) {
//            return (Integer) value;
//        }
//        return null;
//    }
//
//    public static int valueToIntOrZero(Map<String, Object> map, String key) {
//        if (map == null) return 0;
//        Object value = map.get(key);
//        if (value == null) return 0;
//        if (value instanceof Integer) return (int) value;
//        return 0;
//    }
//
//    public static int valueToIntOrNegativeOne(Map<String, Object> map, String key) {
//        if (map == null) return -1;
//        Object value = map.get(key);
//        if (value == null) return -1;
//        if (value instanceof Integer) return (int) value;
//        return -1;
//    }
//
//    public static Timestamp mapToTimestamp(Map<String, Object> map) {
//        if (map == null) return null;
//        int seconds = valueToIntOrZero(map, "_seconds");
//        int nanoseconds = valueToIntOrZero(map, "_nanoseconds");
//        return new Timestamp(seconds, nanoseconds);
//    }
//
//    public static double valueToDoubleOrZero(Map<String, Object> map, String key) {
//        if (map == null) return 0;
//        Object value = map.get(key);
//        if (value == null) return 0;
//        if (value instanceof Double) return (double) value;
//        if (value instanceof Integer) {
//            return (double) (int) value;
//        }
//        return 0;
//    }
//
//    @Nullable
//    public static Double valueToDoubleOrNull(Map<String, Object> map, String key) {
//        if (map == null) return null;
//        Object value = map.get(key);
//        if (value == null) return null;
//        if (value instanceof Double) return (double) value;
//        if (value instanceof Integer) {
//            return (double) (int) value;
//        }
//        return null;
//    }
//
//    public static User.JoinedTeam mapToJoinedTeam(Map<String, Object> map) {
//        if (map == null) return null;
//        User.JoinedTeam joinedTeam = new User.JoinedTeam();
//        joinedTeam.setTeam(mapToTeam((Map<String, Object>) map.get("team")));
//        joinedTeam.setJoinedTimestamp(mapToTimestamp((Map<String, Object>) map.get("joinedTimestamp")));
//        joinedTeam.setStatus(valueToStringOrNull(map, "status"));
//        joinedTeam.setJoinedTimestamp(mapToTimestamp((Map<String, Object>) map.get("joinedTimestamp")));
//        joinedTeam.setLeftTimestamp(mapToTimestamp((Map<String, Object>) map.get("leftTimestamp")));
//        joinedTeam.setKickedTimestamp(mapToTimestamp((Map<String, Object>) map.get("kickedTimestamp")));
//        return joinedTeam;
//    }
//
//    public static LikedTeam mapToLikedTeam(Map<String, Object> map) {
//        if (map == null) return null;
//        LikedTeam likedTeam = new LikedTeam();
//        likedTeam.setTeam(mapToTeam((Map<String, Object>) map.get("team")));
//        likedTeam.setActionTimestamp(mapToTimestamp((Map<String, Object>) map.get("actionTimestamp")));
//        return likedTeam;
//    }
//
//    public static Photo mapToPhoto(Map<String, Object> map) {
//        if (map == null) return null;
//        String id = valueToStringOrNull(map, "id");
//        String photoUrl = valueToStringOrNull(map, "photoUrl");
//        int showOrder = valueToIntOrZero(map, "showOrder");
//        return new Photo(id, photoUrl, showOrder);
//    }
//
//    public static Team.TeamMember mapToTeamMember(Map<String, Object> map) {
//        if (map == null) return null;
//        Team.TeamMember teamMember = new Team.TeamMember();
//        Map<String, Object> playerMap = (Map<String, Object>) map.get("player");
//        User player = mapToUser(playerMap);
//        teamMember.setPlayer(mapToUser(playerMap));
//        teamMember.setTitle(valueToStringOrNull(map, "title"));
//        // invited timestamp
//        Map<String, Object> invitedTimestampMap = (Map<String, Object>) map.get("invitedTimestamp");
//        if (invitedTimestampMap != null)
//            teamMember.setInvitedTimestamp(mapToTimestamp(invitedTimestampMap));
//        // joined timestamp
//        Map<String, Object> joinedTimestampMap = (Map<String, Object>) map.get("joinedTimestamp");
//        if (joinedTimestampMap != null)
//            teamMember.setInvitedTimestamp(mapToTimestamp(joinedTimestampMap));
//        // status
//        teamMember.setStatus(valueToStringOrNull(map, "status"));
//        // left timestamp
//        Map<String, Object> leftTimestampMap = (Map<String, Object>) map.get("invitedTimestamp");
//        if (leftTimestampMap != null) teamMember.setLeftTimestamp(mapToTimestamp(leftTimestampMap));
//        // kicked timestamp
//        Map<String, Object> kickedTimestampMap = (Map<String, Object>) map.get("kickedTimestamp");
//        if (kickedTimestampMap != null)
//            teamMember.setKickedTimestamp(mapToTimestamp(kickedTimestampMap));
//        return teamMember;
//    }
//
//    public static User mapToUser(Map<String, Object> map) {
//        if (map == null) return null;
//        User user = new User();
//        // Set id
//        user.setId(valueToStringOrNull(map, "id"));
//        // Set email
//        user.setEmail(valueToStringOrNull(map, "email"));
//        // Set full name
//        user.setFullName(valueToStringOrNull(map, "fullName"));
//        // Set photos
//        List<Photo> photos = new ArrayList<>();
//        ArrayList<Map<String, Object>> photoMaps = (ArrayList<Map<String, Object>>) map.get("photos");
//        if (photoMaps != null) {
//            for (Map<String, Object> photoMap : photoMaps) {
//                Photo photo = mapToPhoto(photoMap);
//                photos.add(photo);
//            }
//        }
//        user.setPhotos(photos);
//        //Set address
//        user.setAddress(valueToStringOrNull(map, "address"));
//        // Set birthday
//        Map<String, Object> birthdayMap = (Map<String, Object>) map.get("birthday");
//        if (birthdayMap != null) user.setBirthday(mapToTimestamp(birthdayMap));
//        // Set phone
//        user.setPhone(valueToStringOrNull(map, "phone"));
//        // Set joined timestamp
//        user.setJoinedTimestamp(mapToTimestamp((Map<String, Object>) map.get("joinedTimestamp")));
//        // Set active status
//        user.setAccountActive(valueToBooleanOrFalse(map, "accountActive"));
//        // Set height
//        user.setHeight(valueToIntOrZero(map, "height"));
//        // Set weight
//        user.setWeight(valueToIntOrZero(map, "weight"));
//        // Set introduction
//        user.setIntroduction(valueToStringOrNull(map, "introduction"));
//        // Set preferred positions
//        user.setPreferredPositions((Map<String, Object>) map.get("preferredPositions"));
//        // Set schedule
//        // TODO
//        // Set location
//        user.setLocation(mapToLocation((Map<String, Object>) map.get("location")));
//        // Set last update notification timestamp
//        user.setLastUpdateNotificationTimestamp(mapToTimestamp((Map<String, Object>) map.get("lastUpdateNotification")));
//        // Set joined teams
//        List<User.JoinedTeam> joinedTeams = new ArrayList<>();
//        ArrayList<Map<String, Object>> joinedTeamMaps = (ArrayList<Map<String, Object>>) map.get("joinedTeams");
//        if (joinedTeamMaps != null) {
//            for (Map<String, Object> joinedTeamMap : joinedTeamMaps) {
//                User.JoinedTeam joinedTeam = mapToJoinedTeam(joinedTeamMap);
//                joinedTeams.add(joinedTeam);
//            }
//        }
//        user.setJoinedTeams(joinedTeams);
//        // Set liked teams
//        List<LikedTeam> likedTeams = new ArrayList<>();
//        ArrayList<Map<String, Object>> likedTeamMaps = (ArrayList<Map<String, Object>>) map.get("likedTeams");
//        if (likedTeamMaps != null) {
//            for (Map<String, Object> likedTeamMap : likedTeamMaps) {
//                LikedTeam likedTeam = mapToLikedTeam(likedTeamMap);
//                if (likedTeam != null) likedTeams.add(likedTeam);
//            }
//        }
//        user.setLikedTeams(likedTeams);
//        // Set liked by teams
//        List<LikedTeam> likedByTeams = new ArrayList<>();
//        ArrayList<Map<String, Object>> likedByTeamMaps = (ArrayList<Map<String, Object>>) map.get("likedByTeams");
//        if (likedByTeamMaps != null) {
//            for (Map<String, Object> likedByTeamMap : likedByTeamMaps) {
//                LikedTeam likedTeam = mapToLikedTeam(likedByTeamMap);
//                likedByTeams.add(likedTeam);
//            }
//        }
//        user.setLikedByTeams(likedByTeams);
//        return user;
//    }
//
//    public static Team mapToTeam(Map<String, Object> map) {
//        if (map == null) return null;
//        Team team = new Team();
//        team.setId(valueToStringOrNull(map, "id"));
//        team.setName(valueToStringOrNull(map, "name"));
//        team.setAvatar(valueToStringOrNull(map, "avatar"));
//        // set photos
//        List<Photo> photos = new ArrayList<>();
//        ArrayList<Map<String, Object>> photoMaps = (ArrayList<Map<String, Object>>) map.get("photos");
//        if (photoMaps != null) {
//            for (Map<String, Object> photoMap : photoMaps) {
//                Photo photo = mapToPhoto(photoMap);
//                photos.add(photo);
//            }
//        }
//        if (map.get("leader") instanceof String)
//            team.setLeader(new User(valueToStringOrNull(map, "leader")));
//        else team.setLeader(mapToUser((Map<String, Object>) map.get("leader")));
//        team.setPhotos(photos);
//        team.setContactTelephones((ArrayList<String>) map.get("contactTelephones"));
//        team.setContactEmails((ArrayList<String>) map.get("contactEmails"));
//        team.setLocation(mapToLocation((Map<String, Object>) map.get("location")));
//        team.setIntroduction(valueToStringOrNull(map, "introduction"));
//        team.setStatus(valueToStringOrNull(map, "status"));
//        team.setMainGroup(valueToIntOrNegativeOne(map, "mainGroup"));
//        team.setGroups((ArrayList<Integer>) map.get("groups"));
//        // set team members
//        List<Team.TeamMember> teamMembers = new ArrayList<>();
//        ArrayList<Map<String, Object>> teamMemberMaps = (ArrayList<Map<String, Object>>) map.get("teamMembers");
//        if (teamMemberMaps != null) {
//            for (Map<String, Object> teamMemberMap : teamMemberMaps) {
//                Team.TeamMember teamMember = mapToTeamMember(teamMemberMap);
//                teamMembers.add(teamMember);
//            }
//        }
//        team.setTeamMembers(teamMembers);
//        // set schedule TODO
//        // set game history
//        List<Team.PlayedGame> gameHistory = new ArrayList<>();
//        ArrayList<Map<String, Object>> gameHistoryMaps = (ArrayList<Map<String, Object>>) map.get("gameHistory");
//        if (gameHistoryMaps != null) {
//            for (Map<String, Object> gameHistoryMap : gameHistoryMaps) {
//                Team.PlayedGame playedGame = mapToPlayedGame(gameHistoryMap);
//                gameHistory.add(playedGame);
//            }
//        }
//        team.setGamesHistory(gameHistory);
//        // set liked teams
//        List<LikedTeam> likedTeams = new ArrayList<>();
//        ArrayList<Map<String, Object>> likedTeamMaps = (ArrayList<Map<String, Object>>) map.get("likedTeams");
//        if (likedTeamMaps != null) {
//            for (Map<String, Object> likedTeamMap : likedTeamMaps) {
//                LikedTeam likedTeam = mapToLikedTeam(likedTeamMap);
//                if (likedTeam != null) likedTeams.add(likedTeam);
//            }
//        }
//        team.setLikedTeams(likedTeams);
//        // Set liked by teams
//        List<LikedTeam> likedByTeams = new ArrayList<>();
//        ArrayList<Map<String, Object>> likedByTeamMaps = (ArrayList<Map<String, Object>>) map.get("likedByTeams");
//        if (likedByTeamMaps != null) {
//            for (Map<String, Object> likedByTeamMap : likedByTeamMaps) {
//                LikedTeam likedTeam = mapToLikedTeam(likedByTeamMap);
//                if (likedTeam != null) likedByTeams.add(likedTeam);
//            }
//        }
//        team.setLikedByTeams(likedByTeams);
//        team.setLastUpdateNotificationTimestamp(mapToTimestamp((Map<String, Object>) map.get("lastUpdateNotification")));
//        // Set liked players
//        List<Team.LikedPlayer> likedPlayers = new ArrayList<>();
//        ArrayList<Map<String, Object>> likedPlayerMaps = (ArrayList<Map<String, Object>>) map.get("likedPlayers");
//        if (likedPlayerMaps != null) {
//            for (Map<String, Object> likedPlayerMap : likedPlayerMaps) {
//                Team.LikedPlayer likedPlayer = mapToLikedPlayer(likedPlayerMap);
//                if (likedPlayer != null) likedPlayers.add(likedPlayer);
//            }
//        }
//        team.setLikedPlayers(likedPlayers);
//        // Set liked by players
//        List<Team.LikedPlayer> likedByPlayers = new ArrayList<>();
//        ArrayList<Map<String, Object>> likedByPlayerMaps = (ArrayList<Map<String, Object>>) map.get("likedByPlayers");
//        if (likedByPlayerMaps != null) {
//            for (Map<String, Object> likedByPlayerMap : likedByPlayerMaps) {
//                Team.LikedPlayer likedByPlayer = mapToLikedPlayer(likedByPlayerMap);
//                if (likedByPlayer != null) likedByPlayers.add(likedByPlayer);
//            }
//        }
//        team.setLikedByPlayers(likedByPlayers);
//        return team;
//    }
//
//    private static Team.LikedPlayer mapToLikedPlayer(Map<String, Object> map) {
//        if (map == null) return null;
//        Team.LikedPlayer likedPlayer = new Team.LikedPlayer();
//        likedPlayer.setUser(mapToUser((Map<String, Object>) map.get("player")));
//        likedPlayer.setActionTimestamp(mapToTimestamp((Map<String, Object>) map.get("actionTimestamp")));
//        return likedPlayer;
//    }
//
//    public static Schedule mapToSchedule(Map<String, Object> map) {
//        if (map == null) return null;
//        Schedule schedule = new Schedule();
//        schedule.setCreatedTimestamp(mapToTimestamp((Map<String, Object>) map.get("createdTimestamp")));
//        schedule.setGameTimestamp(mapToTimestamp((Map<String, Object>) map.get("gameTimestamp")));
//        schedule.setStadium(mapToStadium((Map<String, Object>) map.get("stadium")));
//        schedule.setTeam1(mapToTeam((Map<String, Object>) map.get("team1")));
//        schedule.setTeam2(mapToTeam((Map<String, Object>) map.get("team2")));
//        return null; //TODO
//    }
//
//    public static Game mapToGame(Map<String, Object> map) {
//        if (map == null) return null;
//        Game game = new Game();
//        game.setId(valueToStringOrNull(map, "id"));
//        game.setTeam1(mapToTeam((Map<String, Object>) map.get("team1")));
//        game.setTeam2(mapToTeam((Map<String, Object>) map.get("team2")));
//        game.setSchedule(mapToSchedule((Map<String, Object>) map.get("schedule")));
//        game.setTeam1Score(valueToIntegerOrNull(map, "team1Score"));
//        game.setTeam2Score(valueToIntegerOrNull(map, "team2Score"));
//        game.setTeam1Rating(mapToGameRating((Map<String, Object>) map.get("team1Rating")));
//        game.setTeam2Rating(mapToGameRating((Map<String, Object>) map.get("team2Rating")));
//        // game comments
//        List<Game.GameComment> gameComments = new ArrayList<>();
//        ArrayList<Map<String, Object>> gameCommentMaps = (ArrayList<Map<String, Object>>) map.get("gameComments");
//        if (gameCommentMaps != null) {
//            for (Map<String, Object> gameCommentMap : gameCommentMaps) {
//                Game.GameComment gameComment = mapToGameComment(gameCommentMap);
//                gameComments.add(gameComment);
//            }
//        }
//        game.setGameComments(gameComments);
//        return game;
//    }
//
//    public static Game.GameRating mapToGameRating(Map<String, Object> map) {
//        if (map == null) return null;
//        Game.GameRating gameRating = new Game.GameRating();
//        gameRating.setTeam1Score(valueToIntOrNegativeOne(map, "team1Score"));
//        gameRating.setTeam2Score(valueToIntOrNegativeOne(map, "team2Score"));
//        gameRating.setFairPlay(valueToDoubleOrZero(map, "fairPlay"));
//        gameRating.setSkills(valueToDoubleOrZero(map, "skills"));
//        return gameRating;
//    }
//
//    public static Team.PlayedGame mapToPlayedGame(Map<String, Object> map) {
//        if (map == null) return null;
//        Team.PlayedGame playedGame = new Team.PlayedGame();
//        playedGame.setGame(mapToGame((Map<String, Object>) map.get("game")));
//        playedGame.setFairPlay(valueToDoubleOrZero(map, "fairPlay"));
//        playedGame.setSkills(valueToDoubleOrZero(map, "skills"));
//        playedGame.setMatchRating(valueToDoubleOrZero(map, "matchRating"));
//        return playedGame;
//    }
//
//    public static Game.GameComment mapToGameComment(Map<String, Object> map) {
//        if (map == null) return null;
//        Game.GameComment gameComment = new Game.GameComment();
//        gameComment.setId(valueToStringOrNull(map, "id"));
//        gameComment.setUser(mapToUser((Map<String, Object>) map.get("user")));
//        gameComment.setCommentType(valueToStringOrNull(map, "commentType"));
//        gameComment.setText(valueToStringOrNull(map, "text"));
//        gameComment.setImageUrl(valueToStringOrNull(map, "imageUrl"));
//        gameComment.setAttachmentUrl(valueToStringOrNull(map, "attachmentUrl"));
//        gameComment.setSentTimestamp(mapToTimestamp((Map<String, Object>) map.get("sentTimestamp")));
//        gameComment.setStatus(valueToStringOrNull(map, "status"));
//        return gameComment;
//    }
//
//    public static Stadium mapToStadium(Map<String, Object> map) {
//        if (map == null) return null;
//        Stadium stadium = new Stadium();
//        stadium.setId(valueToStringOrNull(map, "id"));
//        stadium.setName(valueToStringOrNull(map, "name"));
//        stadium.setLocation(mapToLocation((Map<String, Object>) map.get("location")));
//        stadium.setPricePerHour(valueToIntOrNegativeOne(map, "pricePerHour"));
//        return stadium;
//    }
//
//    public static Location mapToLocation(Map<String, Object> map) {
//        if (map == null) return null;
//        Double longitude = valueToDoubleOrNull(map, "longitude");
//        Double latitude = valueToDoubleOrNull(map, "latitude");
//        String geoHash = valueToStringOrNull(map, "geoHash");
//        if (longitude != null && latitude != null && geoHash != null)
//            return new Location(longitude, latitude, geoHash);
//        else return null;
//    }
}
