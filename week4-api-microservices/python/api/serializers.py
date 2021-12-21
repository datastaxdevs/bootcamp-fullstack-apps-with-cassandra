from rest_framework import serializers
from api.models import TodoItem


class TodoItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = TodoItem
        fields = ('title', 'completed', 'url', 'order')
